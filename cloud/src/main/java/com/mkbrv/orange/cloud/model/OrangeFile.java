package com.mkbrv.orange.cloud.model;

import com.mkbrv.orange.cloud.model.file.DefaultOrangeFile;
import com.mkbrv.orange.cloud.model.file.OrangeFileMetadata;
import com.mkbrv.orange.cloud.model.file.OrangeFileType;

import java.util.Date;


/**
 * Created by mkbrv on 26/02/16.
 */
public interface OrangeFile {

    String getId();

    String getName();

    OrangeFileType getType();

    Long getSize();

    public Date getCreationDate();

    public Date getLastUpdateDate();

    public OrangeFileMetadata getMetadata();

    public String getThumbUrl();

    public String getPreviewUrl();

    public String getDownloadUrl();

    default OrangeFile newInstance(final String id) {
        return new DefaultOrangeFile(id);
    }
}
