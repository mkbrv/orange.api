package com.mkbrv.orange.client.response;


import com.mkbrv.orange.client.request.OrangeRequest;

import java.io.Serializable;

/**
 * Created by mikibrv on 16/02/16.
 */
public class OrangeResponse implements Serializable {

    private static final long serialVersionUID = 42L;

    private Integer status;

    private String body;

    private OrangeRequest orangeRequest;

    public OrangeResponse() {
    }


    public OrangeResponse setOrangeRequest(final OrangeRequest orangeRequest) {
        this.orangeRequest = orangeRequest;
        return this;
    }

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

    public OrangeRequest getOrangeRequest() {
        return orangeRequest;
    }
}
