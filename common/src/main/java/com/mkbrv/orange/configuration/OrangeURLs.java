package com.mkbrv.orange.configuration;

/**
 * Created by mikibrv on 17/02/16.
 */
public class OrangeURLs {

    /**
     * Domain used by Orange
     */
    protected String domain;

    protected String resStatus;

    protected String oauthAuthorize;

    protected String oauthToken;

    public String getDomain() {
        return domain;
    }

    public OrangeURLs setDomain(final String domain) {
        this.domain = domain;
        return this;
    }

    public String getResStatus() {
        return resStatus;
    }

    public OrangeURLs setResStatus(final String resStatus) {
        this.resStatus = resStatus;
        return this;
    }

    public String getOauthAuthorize() {
        return oauthAuthorize;
    }

    public OrangeURLs setOauthAuthorize(final String oauthAuthorize) {
        this.oauthAuthorize = oauthAuthorize;
        return this;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public OrangeURLs setOauthToken(final String oauthToken) {
        this.oauthToken = oauthToken;
        return this;
    }


    public static final OrangeURLs DEFAULT = new OrangeURLs() {

        public static final String DEFAULT_DOMAIN = "https://api.orange.com";
        public static final String DEFAULT_RES_STATUS = "/res/status";
        public static final String DEFAULT_OAUTHORIZE = "/oauth/v2/authorize";
        public static final String DEFAULT_OAUTH_TOKEN = "/oauth/v2/token";

        {
            this.domain = DEFAULT_DOMAIN;
            this.resStatus = DEFAULT_RES_STATUS;
            this.oauthAuthorize = DEFAULT_OAUTHORIZE;
            this.oauthToken = DEFAULT_OAUTH_TOKEN;
        }
    };


}
