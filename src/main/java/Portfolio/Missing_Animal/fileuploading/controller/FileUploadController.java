package Portfolio.Missing_Animal.fileuploading.controller;

import Portfolio.Missing_Animal.fileuploading.service.StorageService;
import Portfolio.Missing_Animal.fileuploading.storage.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

//@Controller
@Slf4j
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "test";
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") List<MultipartFile> file,
								   RedirectAttributes redirectAttributes) {


		storageService.store(file);


		//redirectAttributes.addFlashAttribute("message",
		//		"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws UnsupportedEncodingException {


		Resource file = storageService.loadAsResource(filename);


		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + URLEncoder.encode(file.getFilename(),"UTF-8") + "\"")
				.body(file);

	}



	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
