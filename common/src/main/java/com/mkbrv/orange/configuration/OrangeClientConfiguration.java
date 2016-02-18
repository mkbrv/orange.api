package com.mkbrv.orange.configuration;

/**
 * As in what concerns credentials for orange apps;
 * Created by mikibrv on 16/02/16.
 */
public class OrangeClientConfiguration {

    private final String appId;

    private final String clientSecret;

    private final String clientId;

    private final String appRedirectURL;

    /**
     * @param appId
     * @param clientId
     * @param clientSecret
     * @param appRedirectURL
     */
    public OrangeClientConfiguration(final String appId, final String clientId,
                                     final String clientSecret, final String appRedirectURL) {
        this.appId = appId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.appRedirectURL = appRedirectURL;
    }

    public String getAppId() {
        return appId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getAppRedirectURL() {
        return appRedirectURL;
    }

    public String getClientId() {
        return clientId;
    }
}
