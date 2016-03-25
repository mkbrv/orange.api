package com.mkbrv.orange.httpclient.security;

import java.util.Date;

/**
 * Created by mkbrv on 18/02/16.
 */
public final class OrangeRefreshToken {

    private final String token;
    private Date createdDate;

    public OrangeRefreshToken(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public OrangeRefreshToken setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Override
    public String toString() {
        return "OrangeRefreshToken{" +
                "token='" + token + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrangeRefreshToken that = (OrangeRefreshToken) o;

        return token.equals(that.token);

    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }
}
