package com.mkbrv.orange.cloud.model;

import java.util.Date;

/**
 * Created by mikibrv on 26/02/16.
 */
public class OrangeFile {

    private String id;

    private String name;

    private OrangeFileType type;

    private OrangeFileMetadata metadata;

    private Long size;

    private Date creationDate;

    private Date lastUpdateDate;

    public String getId() {
        return id;
    }

    public OrangeFile setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrangeFile setName(String name) {
        this.name = name;
        return this;
    }

    public OrangeFileType getType() {
        return type;
    }

    public OrangeFile setType(OrangeFileType type) {
        this.type = type;
        return this;
    }


    public Long getSize() {
        return size;
    }

    public OrangeFile setSize(Long size) {
        this.size = size;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public OrangeFile setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public OrangeFile setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public OrangeFileMetadata getMetadata() {
        return metadata;
    }

    public OrangeFile setMetadata(OrangeFileMetadata metadata) {
        this.metadata = metadata;
        return this;
    }
}
