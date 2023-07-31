package Portfolio.Missing_Animal.service.serviceinterface;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageServiceForReport {

    public void init();

    public void store(MultipartFile file);

    public Stream<Path> loadAll();

    public Path load(String filename);

    public Resource loadAsResource(String filename);

    public void deleteAll();

    public void deleteOne(String fileName) throws IOException;



}
