package com.mkbrv.orange.cloud.request;

/**
 * Contains parameters used to update / create a folder
 * Created by mkbrv on 08/03/16.
 */
public class OrangeFolderRequestParams {

    private Boolean clone;

    private String name;

    private String parentFolderId;

    public Boolean getClone() {
        return clone;
    }

    public OrangeFolderRequestParams setClone(Boolean clone) {
        this.clone = clone;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrangeFolderRequestParams setName(String name) {
        this.name = name;
        return this;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public OrangeFolderRequestParams setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
        return this;
    }
}
