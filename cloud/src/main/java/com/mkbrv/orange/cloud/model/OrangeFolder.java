package com.mkbrv.orange.cloud.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikibrv on 21/02/16.
 */
public class OrangeFolder {

    private String id;

    private String parentFolderId;

    private String name;

    private List<OrangeFile> files = new ArrayList<>();

    private List<OrangeFolder> subFolders = new ArrayList<>();

    public String getId() {
        return id;
    }

    public OrangeFolder setId(String id) {
        this.id = id;
        return this;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public OrangeFolder setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrangeFolder setName(String name) {
        this.name = name;
        return this;
    }

    public List<OrangeFile> getFiles() {
        return files;
    }

    public OrangeFolder addFile(final OrangeFile orangeFile) {
        this.files.add(orangeFile);
        return this;
    }

    public OrangeFolder addFiles(final List<OrangeFile> orangeFiles) {
        this.files.addAll(orangeFiles);
        return this;
    }

    public List<OrangeFolder> getSubFolders() {
        return subFolders;
    }

    public OrangeFolder addSubFolders(List<OrangeFolder> subFolders) {
        this.subFolders.addAll(subFolders);
        return this;
    }

    public OrangeFolder addSubFolder(OrangeFolder subFolder) {
        this.subFolders.add(subFolder);
        return this;
    }
}
