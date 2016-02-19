package com.mkbrv.orange.client.security;

import java.util.Date;

/**
 * Created by mikibrv on 18/02/16.
 */
public class OrangeAccessToken {

    private final String token;

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
}
