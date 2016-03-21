package com.mkbrv.orange.httpclient;

import com.mkbrv.orange.httpclient.request.OrangeRequest;
import com.mkbrv.orange.httpclient.response.OrangeResponse;

/**
 * Implementation required in order to send an HTTP request to Orange API.
 * Default implementation can be found in the http-httpclient module which uses Apache HTTP Client;
 * Created by mkbrv on 16/02/16.
 */
public interface OrangeHttpClient {

    /**
     * Performs a GET request to a specific URL
     *
     * @param request OrangeRequest
     * @return OrangeResponse
     */
    OrangeResponse doGet(final OrangeRequest request);

    /**
     * @param request OrangeRequest
     * @return OrangeResponse
     */
    OrangeResponse doPost(final OrangeRequest request);


    /**
     * @param request OrangeRequest
     * @return OrangeResponse
     */
    OrangeResponse delete(final OrangeRequest request);

}
