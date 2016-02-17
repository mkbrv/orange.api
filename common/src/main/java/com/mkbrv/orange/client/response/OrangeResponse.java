package com.mkbrv.orange.client.response;


import java.io.Serializable;

/**
 * Created by mikibrv on 16/02/16.
 */
public class OrangeResponse implements Serializable {

    private static final long serialVersionUID = 42L;

    Integer status;

    private String body;

    public OrangeResponse setStatus(final Integer status) {
        this.status = status;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Object getBody() {
        return body;
    }

    public OrangeResponse setBody(String body) {
        this.body = body;
        return this;
    }
}
