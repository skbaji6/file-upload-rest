package com.example.filedemo.payload;

public class UploadFileResponse {
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private ImageProp imageProp;
	private PdfProp pdfProp;

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

	public ImageProp getImageProp() {
		return imageProp;
	}

	public void setImageProp(ImageProp imageProp) {
		this.imageProp = imageProp;
	}

	public PdfProp getPdfProp() {
		return pdfProp;
	}

	public void setPdfProp(PdfProp pdfProp) {
		this.pdfProp = pdfProp;
	}

}
