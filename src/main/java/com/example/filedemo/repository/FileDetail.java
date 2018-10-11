package com.example.filedemo.repository;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FileDetail {
	
	@Id
	@GeneratedValue
	private Long id;
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private Long size;
    private String creationDate;
    private String modificationDate;
    private String imageResolution;
    private Double meanOfRed;
    private Double meanOfGreen;
    private Double meanOfBlue;
    private Double meanOfRGB;
    private String paperSize;
	private String totalNoOfLines;
	private String totalNoOfWords;
	private String docStatus;
	private Date uploadedDate;
	
	public FileDetail() {
		super();
	}
	public FileDetail(String fileName, String fileDownloadUri, String fileType,
			Long size, String creationDate, String modificationDate,
			String docStatus) {
		super();
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.docStatus = docStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}
	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}
	public String getImageResolution() {
		return imageResolution;
	}
	public void setImageResolution(String imageResolution) {
		this.imageResolution = imageResolution;
	}
	public Double getMeanOfRed() {
		return meanOfRed;
	}
	public void setMeanOfRed(Double meanOfRed) {
		this.meanOfRed = meanOfRed;
	}
	public Double getMeanOfGreen() {
		return meanOfGreen;
	}
	public void setMeanOfGreen(Double meanOfGreen) {
		this.meanOfGreen = meanOfGreen;
	}
	public Double getMeanOfBlue() {
		return meanOfBlue;
	}
	public void setMeanOfBlue(Double meanOfBlue) {
		this.meanOfBlue = meanOfBlue;
	}
	public Double getMeanOfRGB() {
		return meanOfRGB;
	}
	public void setMeanOfRGB(Double meanOfRGB) {
		this.meanOfRGB = meanOfRGB;
	}
	public String getPaperSize() {
		return paperSize;
	}
	public void setPaperSize(String paperSize) {
		this.paperSize = paperSize;
	}
	public String getTotalNoOfLines() {
		return totalNoOfLines;
	}
	public void setTotalNoOfLines(String totalNoOfLines) {
		this.totalNoOfLines = totalNoOfLines;
	}
	public String getTotalNoOfWords() {
		return totalNoOfWords;
	}
	public void setTotalNoOfWords(String totalNoOfWords) {
		this.totalNoOfWords = totalNoOfWords;
	}
	public String getDocStatus() {
		return docStatus;
	}
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	
	

}
