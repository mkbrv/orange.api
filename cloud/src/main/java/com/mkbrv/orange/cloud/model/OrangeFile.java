package com.mkbrv.orange.cloud.model;

import com.mkbrv.orange.cloud.model.file.DefaultOrangeFile;
import com.mkbrv.orange.cloud.model.file.OrangeFileMetadata;
import com.mkbrv.orange.cloud.model.file.OrangeFileType;

import java.util.Date;


/**
 * OrangeFile as described in https://developer.orange.com/apis/cloud-france/api-reference.
 * Depending on scope or specific parameters sent in the request only partial data will be returned;
 * Created by mkbrv on 26/02/16.
 */
public interface OrangeFile {

    /**
     * @return id of the orange file
     */
    String getId();

    /**
     * @return name of the file
     */
    String getName();

    /**
     * @return file type (image|video|audio)
     */
    OrangeFileType getType();

    /**
     * @return file size
     */
    Long getSize();

    /**
     * @return creationDate
     */
    public Date getCreationDate();

    /**
     * @return lastUpdateDate
     */
    public Date getLastUpdateDate();

    /**
     * @return wrapper over a map containing key value metadata
     */
    public OrangeFileMetadata getMetadata();

    /**
     * @return thumbnail url;
     */
    public String getThumbUrl();

    /**
     * @return preview url
     */
    public String getPreviewUrl();

    /**
     * @return downloadUrl
     */
    public String getDownloadUrl();

    /**
     * Default instance with an id;
     *
     * @param id
     * @return
     */
    static OrangeFile newInstance(final String id) {
        return new DefaultOrangeFile(id);
    }

    /**
     * Default instance similar to a copy constructor;
     *
     * @param orangeFile original file
     * @return copy of the file;
     */
    static OrangeFile newInstance(final OrangeFile orangeFile) {
        return new DefaultOrangeFile(orangeFile.getId())
                .setDownloadUrl(orangeFile.getDownloadUrl())
                .setPreviewUrl(orangeFile.getPreviewUrl())
                .setThumbUrl(orangeFile.getThumbUrl())
                .setSize(orangeFile.getSize())
                .setCreationDate(orangeFile.getCreationDate())
                .setLastUpdateDate(orangeFile.getLastUpdateDate())
                .setName(orangeFile.getName())
                .setType(orangeFile.getType())
                .setMetadata(new OrangeFileMetadata(orangeFile.getMetadata()));
    }
}
