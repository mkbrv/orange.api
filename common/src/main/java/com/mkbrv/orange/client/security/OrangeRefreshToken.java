package com.mkbrv.orange.client.security;

import java.util.Date;

/**
 * Created by mikibrv on 18/02/16.
 */
public class OrangeRefreshToken {

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
}
