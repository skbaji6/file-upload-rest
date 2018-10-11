package com.example.filedemo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
    private Integer minutesWaitToDeleteFile; 

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

	public Integer getMinutesWaitToDeleteFile() {
		return minutesWaitToDeleteFile;
	}

	public void setMinutesWaitToDeleteFile(Integer minutesWaitToDeleteFile) {
		this.minutesWaitToDeleteFile = minutesWaitToDeleteFile;
	}
}
