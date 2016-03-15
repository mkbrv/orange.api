package com.mkbrv.orange.cloud.model.folder;

import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.OrangeFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkbrv on 21/02/16.
 */
public class DefaultOrangeFolder implements OrangeFolder {

    /**
     * Unique identifier.
     */
    private String id;

    private String parentFolderId;

    private String name;

    private List<OrangeFile> files = new ArrayList<>();

    private List<OrangeFolder> subFolders = new ArrayList<>();


    public DefaultOrangeFolder() {
    }

    public DefaultOrangeFolder(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public DefaultOrangeFolder setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DefaultOrangeFolder setName(String name) {
        this.name = name;
        return this;
    }

    public List<OrangeFile> getFiles() {
        return files;
    }

    public DefaultOrangeFolder addFile(final OrangeFile orangeFile) {
        this.files.add(orangeFile);
        return this;
    }

    public DefaultOrangeFolder addFiles(final List<OrangeFile> orangeFiles) {
        this.files.addAll(orangeFiles);
        return this;
    }

    public List<OrangeFolder> getSubFolders() {
        return subFolders;
    }

    public DefaultOrangeFolder addSubFolders(List<OrangeFolder> subFolders) {
        this.subFolders.addAll(subFolders);
        return this;
    }

    @Override
    public OrangeFolder addSubFolder(OrangeFolder subFolder) {
        this.subFolders.add(subFolder);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultOrangeFolder that = (DefaultOrangeFolder) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
