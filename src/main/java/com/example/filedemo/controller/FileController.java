package com.example.filedemo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.filedemo.repository.FileDetail;
import com.example.filedemo.service.FileStorageService;
import com.example.filedemo.utils.UploadUtil;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private UploadUtil uploadUtil;

	@PostMapping("/uploadFile")
	public ResponseEntity<List<FileDetail>> uploadFile(@RequestParam("file") MultipartFile file) {
		if (uploadUtil.isInvalidFile(file)) {
			return ResponseEntity.badRequest().body(new ArrayList<FileDetail>());
		}
		fileStorageService.storeFile(file);		
		return getAllUploadedFiles();
	}

	@GetMapping("/downloadFile/{fileName:.+}/{accessType}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,@PathVariable String accessType, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		BodyBuilder builder=ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType);
		if(accessType.equals("download")){
			builder=builder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		}
		return builder.body(resource);
	}
	
	@GetMapping("/getAllUploadedFiles")
	public ResponseEntity<List<FileDetail>> getAllUploadedFiles() {
		List<FileDetail> fileDetails= fileStorageService.getAllFileDetails();
		return new ResponseEntity<List<FileDetail>>(fileDetails,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteFile/{fileId}")
	public ResponseEntity<List<FileDetail>> deleteFile(@PathVariable("fileId") Long fileId) {
		fileStorageService.changeStatusOfFile(fileId,"D");
		return new ResponseEntity<List<FileDetail>>(fileStorageService.getAllFileDetails(),HttpStatus.OK);
	}
	
	@GetMapping("/getAllDeletedFiles")
	public ResponseEntity<List<FileDetail>> getAllDeletedFiles() {
		List<FileDetail> fileDetails= fileStorageService.getAllDeletedFileDetails();
		return new ResponseEntity<List<FileDetail>>(fileDetails,HttpStatus.OK);
	}
	
	@GetMapping("/recoverFile/{fileId}")
	public ResponseEntity<List<FileDetail>> recoverFile(@PathVariable("fileId") Long fileId) {
		fileStorageService.changeStatusOfFile(fileId,"A");
		return new ResponseEntity<List<FileDetail>>(fileStorageService.getAllDeletedFileDetails(),HttpStatus.OK);
	}

}
