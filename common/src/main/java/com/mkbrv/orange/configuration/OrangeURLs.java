package com.mkbrv.orange.configuration;

/**
 * URL's used by Orange. it is configurable with a default.
 * In case they maybe change their "v" in the URL;
 * Created by mkbrv on 17/02/16.
 */
public class OrangeURLs {

    /**
     * Domain used by Orange
     */
    protected String domain;

    protected String resStatus;

    protected String oauthAuthorize;

    protected String oauthToken;

    protected String freeSpace;

    protected String folders;

    protected String files;

    protected String uploadFile;

    public String getDomain() {
        return domain;
    }

    public String getResStatus() {
        return resStatus;
    }

    public String getOauthAuthorize() {
        return oauthAuthorize;
    }

    public String getOauthToken() {
        return this.domain + oauthToken;
    }

    public String getFreeSpace() {
        return this.domain + freeSpace;
    }


    public String getFolders() {
        return this.domain + folders;
    }

    public String getFiles() {
        return this.domain + files;
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public static final OrangeURLs DEFAULT = new OrangeURLs() {

        public static final String DEFAULT_DOMAIN = "https://api.orange.com";
        public static final String DEFAULT_RES_STATUS = "/res/status";
        public static final String DEFAULT_OAUTHORIZE = "/oauth/v2/authorize";
        public static final String DEFAULT_OAUTH_TOKEN = "/oauth/v2/token";

        public static final String DEFAULT_FREESPACE = "/cloud/v1/freespace";
        public static final String DEFAULT_ROOT_FOLDER = "/cloud/v1/folders";

        public static final String DEFAULT_FILES = "/cloud/v1/files";

        public static final String DEFAULT_UPLOAD_FILE = "/cloud/v1/files/content";


        {
            this.domain = DEFAULT_DOMAIN;
            this.resStatus = DEFAULT_RES_STATUS;
            this.oauthAuthorize = DEFAULT_OAUTHORIZE;
            this.oauthToken = DEFAULT_OAUTH_TOKEN;
            this.freeSpace = DEFAULT_FREESPACE;
            this.folders = DEFAULT_ROOT_FOLDER;
            this.files = DEFAULT_FILES;
            this.uploadFile = DEFAULT_UPLOAD_FILE;
        }
    };


}
