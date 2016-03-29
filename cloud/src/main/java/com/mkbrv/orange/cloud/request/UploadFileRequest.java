package com.mkbrv.orange.cloud.request;

import com.mkbrv.orange.cloud.model.OrangeFolder;

import java.io.InputStream;

/**
 * Created by mkbrv on 29/03/16.
 */
public class UploadFileRequest {

    private InputStream file;

    private Integer fileSize;

    private String name;

    private OrangeFolder parentFolder;

    public UploadFileRequest(InputStream file, String name, OrangeFolder parentFolder) {
        this.file = file;
        this.name = name;
        this.parentFolder = parentFolder;
    }

    public InputStream getFile() {
        return file;
    }

    public UploadFileRequest setFile(InputStream file) {
        this.file = file;
        return this;
    }

    public String getName() {
        return name;
    }

    public UploadFileRequest setName(String name) {
        this.name = name;
        return this;
    }

    public OrangeFolder getParentFolder() {
        return parentFolder;
    }

    public UploadFileRequest setParentFolder(OrangeFolder parentFolder) {
        this.parentFolder = parentFolder;
        return this;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public UploadFileRequest setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
        return this;
    }
}
