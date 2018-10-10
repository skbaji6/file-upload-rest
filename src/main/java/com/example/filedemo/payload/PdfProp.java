package com.example.filedemo.payload;

public class PdfProp {
	private String paperSize;
	private String totalNoOfLines;
	private String totalNoOfWords;

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

}
