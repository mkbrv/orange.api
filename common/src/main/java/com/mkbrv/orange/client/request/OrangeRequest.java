package com.mkbrv.orange.client.request;


import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.security.OrangeAccessToken;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mkbrv on 16/02/16.
 */
public class OrangeRequest implements Serializable {

    private static final long serialVersionUID = 42L;

    private String url;

    private final Map<String, String> headers = new HashMap<>();

    private final Map<String, String> parameters = new HashMap<>();

    /**
     * Json content for the request;
     */
    private String body;

    private OrangeContext orangeContext;

    private OrangeAccessToken orangeAccessToken;


    /**
     * @param url
     * @return
     */
    public OrangeRequest setUrl(final String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Add a header
     *
     * @param header
     * @param value
     */
    public OrangeRequest addHeader(final String header, final String value) {
        this.headers.put(header, value);
        return this;
    }


    public OrangeRequest addParameter(final String key, final String value) {
        this.parameters.put(key, value);
        return this;
    }

    public OrangeRequest addParameter(final String key, final Object value) {
        if (value == null) {
            this.parameters.put(key, null);
        } else {
            this.parameters.put(key, String.valueOf(value));
        }
        return this;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(this.parameters);
    }


    public String getBody() {
        return body;
    }

    public OrangeRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public OrangeContext getOrangeContext() {
        return orangeContext;
    }

    public OrangeRequest setOrangeContext(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        return this;
    }

    public OrangeAccessToken getOrangeAccessToken() {
        return orangeAccessToken;
    }

    public OrangeRequest setOrangeAccessToken(OrangeAccessToken orangeAccessToken) {
        this.orangeAccessToken = orangeAccessToken;
        return this;
    }

    public Boolean hasContent() {
        return this.body != null && !this.body.isEmpty();
    }

    @Override
    public String toString() {
        return this.getUrl();
    }


}
