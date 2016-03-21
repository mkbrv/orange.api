package com.mkbrv.orange.httpclient.security;

/**
 * TODO: Using scope: openid retrieve user SSO and maybe other data;
 * Created by mkbrv on 11/03/16.
 */
public class OrangeUser {

    private final String ssoId;

    public OrangeUser(final String ssoId) {
        this.ssoId = ssoId;
    }

    public String getSsoId() {
        return ssoId;
    }
}
