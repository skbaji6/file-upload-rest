package com.example.filedemo.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.filedemo.exception.FileStorageException;
import com.example.filedemo.exception.MyFileNotFoundException;
import com.example.filedemo.payload.UploadFileResponse;
import com.example.filedemo.property.FileStorageProperties;
import com.example.filedemo.utils.UploadUtil;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	@Autowired
	private UploadUtil uploadUtil;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with
			// the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

	public List<UploadFileResponse> loadFilesAsResponse() {
		List<UploadFileResponse> responses=null;
		try {
			responses=Files.list(fileStorageLocation).filter(Files::isRegularFile).map(filePath ->getUploadFileResponse(filePath)).collect(Collectors.toList());
		} catch (Exception ex) {
			throw new FileStorageException("Could not create/load FilesAsResponse.",
					ex);
		}
		return responses;
	}
	
	public String getFileDownloadURL(String filename) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(filename).toUriString();
	}
	
	public UploadFileResponse getUploadFileResponse(Path path) {
		try {
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			UploadFileResponse res=null;
			if(Files.probeContentType(path).contains("pdf")){
				
			}else{
				res=new UploadFileResponse(path.getFileName().toString(), getFileDownloadURL(path.getFileName().toString()),
				        Files.probeContentType(path), Files.size(path),attr.creationTime().toString(),attr.lastModifiedTime().toString(),);
			}
			return res;
		} catch (IOException ex) {
			throw new FileStorageException("Could not create UploadFileResponse.",
					ex);
		}
	}
	
	public String getImageResolution(Path path) {
		BufferedImage image=uploadUtil.getBufferedImage(path.toString());
		uploadUtil.
	}
	
}
