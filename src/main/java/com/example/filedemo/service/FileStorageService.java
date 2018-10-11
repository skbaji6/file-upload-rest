package com.example.filedemo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.filedemo.exception.FileStorageException;
import com.example.filedemo.exception.MyFileNotFoundException;
import com.example.filedemo.payload.ImageProp;
import com.example.filedemo.payload.PdfProp;
import com.example.filedemo.property.FileStorageProperties;
import com.example.filedemo.repository.FileDetail;
import com.example.filedemo.repository.FilesRepository;
import com.example.filedemo.utils.UploadUtil;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	private final Integer minutesWaitToDeleteFile;
	
	@Autowired
	private UploadUtil uploadUtil;
	
	@Autowired
	private FilesRepository filesRepository;
	
	public static final String PDF="Pdf";
	public static final String IMAGE="Image";
	public static final long ONE_MINUTE_IN_MILLIS=60000;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		this.minutesWaitToDeleteFile=fileStorageProperties.getMinutesWaitToDeleteFile();
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
			
			Path filepath=Paths.get(fileStorageLocation+File.separator+fileName);
			BasicFileAttributes attributes = Files.readAttributes(filepath, BasicFileAttributes.class);
			
            Date creationDate = new Date(attributes.creationTime().toMillis());
            Date modifiedDate = new Date(attributes.lastModifiedTime().toMillis());
			String fileType=Files.probeContentType(filepath).contains("pdf")?PDF:IMAGE;
			FileDetail fileDetail= new FileDetail(fileName,getFileDownloadURL(fileName),fileType,Files.size(filepath),creationDate.toString(),modifiedDate.toString(),"A");
			if(fileType.equals(IMAGE)){
				ImageProp imageProp=uploadUtil.getImageDetails(uploadUtil.getBufferedImage(fileStorageLocation+File.separator+fileName));
				BeanUtils.copyProperties(imageProp, fileDetail);
			}
			if(fileType.equals(PDF)){
				PdfProp pdfProp=uploadUtil.getPdfDetails(fileStorageLocation+File.separator+fileName);
					BeanUtils.copyProperties(pdfProp, fileDetail);
			}
			fileDetail.setUploadedDate(new Date());
			filesRepository.save(fileDetail);

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
	
	public String getFileDownloadURL(String filename) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(filename).toUriString();
	}
	
	

	public List<FileDetail> getAllFileDetails() {
		List<FileDetail> allFiles=filesRepository.findAll();
		return allFiles.stream().map(filteredFile -> {
			if(filteredFile.getDocStatus().equals("A") && new Date(filteredFile.getUploadedDate().getTime()+(minutesWaitToDeleteFile*ONE_MINUTE_IN_MILLIS)).before(new Date())){
				deleteFile(filteredFile.getId());
			}
			return filteredFile;
			}).filter(file ->file.getDocStatus().equals("A")).collect(Collectors.toList());
	}
	
	public void deleteFile(Long fileId) {
		Optional<FileDetail> filedetail=filesRepository.findById(fileId);
		filedetail.ifPresent(file -> {file.setDocStatus("D");
			filesRepository.save(file);});
	}
	
}
