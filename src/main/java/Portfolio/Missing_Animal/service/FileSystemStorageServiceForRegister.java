package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.exception.StorageFileNotFoundException;
import Portfolio.Missing_Animal.propertiesWithJava.StoragePropertiesForRegister;
import Portfolio.Missing_Animal.exception.StorageException;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemStorageServiceForRegister implements StorageServiceForRegister {

	private final Path rootLocation; // rootLoacation == upload-dir(상대경로)

	@Autowired
	public FileSystemStorageServiceForRegister(StoragePropertiesForRegister properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {


		try {

			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}

			// 파일이 저장되는 절대 경로를 생성!
			// destinationFile == C:\\MissingAnimal\\Missing_Animal\\upload-dir\\스크린샷(1013).png
			Path destinationFile = this.rootLocation.resolve(
							Paths.get(file.getOriginalFilename())) // file.getOriginalFilename == 스크린샷(1013).png
					.normalize() // upload-dir\\스크린샷(1013).png
					.toAbsolutePath();  // C:\\MissingAnimal\\Missing_Animal\\upload-dir\\스크린샷(1013).png

			// destinationFile.getParent() == C:\\MissingAnimal\\Missing_Animal\\upload-dir
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
						StandardCopyOption.REPLACE_EXISTING); // 파일이 기존에 존재하는 경우, 덮어 쓰기를 해주는 옵션이다.
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}


	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {

		Path resolve = rootLocation.resolve(filename);

		return resolve;

	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {

		FileSystemUtils.deleteRecursively(rootLocation.toFile());

	}

	@Override
	public void deleteOne(String fileName) throws IOException {

		// 파일이 저장되는 절대 경로를 생성!
		// destinationFile == C:\\MissingAnimal\\Missing_Animal\\upload-dir\\스크린샷(1013).png
		Path destinationFile = this.rootLocation.resolve(
						Paths.get(fileName)) // file.getOriginalFilename == 스크린샷(1013).png
				.normalize() // upload-dir\\스크린샷(1013).png
				.toAbsolutePath();  // C:\\MissingAnimal\\Missing_Animal\\upload-dir\\스크린샷(1013).png

		FileSystemUtils.deleteRecursively(destinationFile);

	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
