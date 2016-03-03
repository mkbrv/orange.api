package com.mkbrv.orange.cloud.model;

import java.util.Date;

/**
 * Created by mikibrv on 26/02/16.
 */
public class OrangeFileMetadata {

    private Integer height;

    private Integer width;

    private Date shootingDate;


    public Integer getHeight() {
        return height;
    }

    public OrangeFileMetadata setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public OrangeFileMetadata setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Date getShootingDate() {
        return shootingDate;
    }

    public OrangeFileMetadata setShootingDate(Date shootingDate) {
        this.shootingDate = shootingDate;
        return this;
    }
}
