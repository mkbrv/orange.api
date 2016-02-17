package com.mkbrv.orange.configuration;

/**
 * As in what concerns credentials for orange apps;
 * Created by mikibrv on 16/02/16.
 */
public class OrangeClientConfiguration {

    private final String appKey;

    private final String appSecret;

    private final String appRedirectURL;

    /**
     * @param appKey
     * @param appSecret
     * @param appRedirectURL
     */
    public OrangeClientConfiguration(final String appKey, final String appSecret, final String appRedirectURL) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.appRedirectURL = appRedirectURL;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAppRedirectURL() {
        return appRedirectURL;
    }
}
