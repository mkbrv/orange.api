package com.mkbrv.orange.httpclient.request;

import java.io.InputStream;

/**
 * Created by mkbrv on 17/02/16.
 */
public class OrangeUploadFileRequest extends OrangeRequest {

    private InputStream file;


    public InputStream getFile() {
        return file;
    }

    public OrangeUploadFileRequest setFile(InputStream file) {
        this.file = file;
        return this;
    }
}
