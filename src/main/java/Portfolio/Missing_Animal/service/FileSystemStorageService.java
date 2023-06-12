package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.exception.StorageFileNotFoundException;
import Portfolio.Missing_Animal.propertiesWithJava.StorageProperties;
import Portfolio.Missing_Animal.exception.StorageException;
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
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation; // rootLoacation == upload-dir(상대경로)

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(List<MultipartFile> files) {

		log.info("ssssssssssssss");

		for (MultipartFile file : files)

		{


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
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
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

		return rootLocation.resolve(filename);

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
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
