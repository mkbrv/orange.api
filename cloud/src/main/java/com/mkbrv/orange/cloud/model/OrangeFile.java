package com.mkbrv.orange.cloud.model;

import java.util.Date;

/**
 * Created by mkbrv on 26/02/16.
 */
public class OrangeFile {

    private String id;

    private String name;

    private String thumbUrl;

    private String previewUrl;

    private OrangeFileType type;

    private OrangeFileMetadata metadata;

    private Long size;

    private Date creationDate;

    private Date lastUpdateDate;

    public OrangeFile() {
    }

    public OrangeFile(final String id) {
        this.id = id;
    }


    public String getId() {
        return id;
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

    public String getThumbUrl() {
        return thumbUrl;
    }

    public OrangeFile setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public OrangeFile setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrangeFile that = (OrangeFile) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
