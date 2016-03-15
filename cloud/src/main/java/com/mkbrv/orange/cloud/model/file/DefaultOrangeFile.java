package com.mkbrv.orange.cloud.model.file;

import com.mkbrv.orange.cloud.model.OrangeFile;

import java.util.Date;

/**
 * Created by mkbrv on 15/03/16.
 */
public class DefaultOrangeFile implements OrangeFile {

    private String id;

    private String name;

    private String thumbUrl;

    private String previewUrl;

    private OrangeFileType type;

    private OrangeFileMetadata metadata;

    private Long size;

    private Date creationDate;

    private Date lastUpdateDate;

    private String downloadUrl;

    public DefaultOrangeFile() {

    }

    public DefaultOrangeFile(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public DefaultOrangeFile setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public DefaultOrangeFile setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getThumbUrl() {
        return thumbUrl;
    }

    public DefaultOrangeFile setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    @Override
    public String getPreviewUrl() {
        return previewUrl;
    }

    public DefaultOrangeFile setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    @Override
    public OrangeFileType getType() {
        return type;
    }

    public DefaultOrangeFile setType(OrangeFileType type) {
        this.type = type;
        return this;
    }

    @Override
    public OrangeFileMetadata getMetadata() {
        return metadata;
    }

    public DefaultOrangeFile setMetadata(OrangeFileMetadata metadata) {
        this.metadata = metadata;
        return this;
    }

    @Override
    public Long getSize() {
        return size;
    }

    public DefaultOrangeFile setSize(Long size) {
        this.size = size;
        return this;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    public DefaultOrangeFile setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public DefaultOrangeFile setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    @Override
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public DefaultOrangeFile setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }
}
