package com.mkbrv.orange.httpclient.security;

import java.util.Date;

/**
 * Created by mkbrv on 18/02/16.
 */
public final class OrangeAccessToken {

    private final String token;

    /**
     * User associated to this account;
     */
    private OrangeUser orangeUser;

    private String tokenType;

    private OrangeRefreshToken refreshToken;

    private Date expirationTime;


    public OrangeAccessToken(final String accessToken) {
        this.token = accessToken;
    }

    public OrangeAccessToken setRefreshToken(final OrangeRefreshToken refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public OrangeRefreshToken getRefreshToken() {
        return refreshToken;
    }

    public OrangeAccessToken setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public String getToken() {
        return token;
    }

    public OrangeAccessToken setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public OrangeAccessToken setOrangeUser(OrangeUser orangeUser) {
        this.orangeUser = orangeUser;
        return this;
    }

    public OrangeUser getOrangeUser() {
        return orangeUser;
    }

    public String getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "OrangeAccessToken{" +
                "token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken=" + refreshToken +
                ", expirationTime=" + expirationTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrangeAccessToken that = (OrangeAccessToken) o;

        return token != null ? token.equals(that.token) : that.token == null;

    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
