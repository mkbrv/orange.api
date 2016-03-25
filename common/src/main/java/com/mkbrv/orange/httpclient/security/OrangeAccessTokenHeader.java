package com.mkbrv.orange.httpclient.security;

import com.mkbrv.orange.httpclient.OrangeContext;

import java.util.Base64;

/**
 * Created by mkbrv on 03/03/16.
 */
public class OrangeAccessTokenHeader {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTHORIZATION_BASIC = "Basic";
    public static final String BEARER = "Bearer";

    private final OrangeContext orangeContext;


    public OrangeAccessTokenHeader(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
    }


    public String getHeaderValue() {
        return OrangeAccessTokenHeader.HEADER_AUTHORIZATION_BASIC + " "
                + new String(Base64.getEncoder().encode((orangeContext.getOrangeClientConfiguration().getClientId()
                + ":" + orangeContext.getOrangeClientConfiguration().getClientSecret()).getBytes()));
    }
}
