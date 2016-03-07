package com.mkbrv.orange.client;

import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;

/**
 * Implementation required in order to send an HTTP request to Orange API.
 * Default implementation can be found in the http-client module which uses Apache HTTP Client;
 * Created by mkbrv on 16/02/16.
 */
public interface OrangeHttpClient {

    /**
     * Performs a GET request to a specific URL
     *
     * @param request
     */
    OrangeResponse doGet(final OrangeRequest request);

    /**
     * @param request
     */
    OrangeResponse doPost(final OrangeRequest request);


    /**
     * @param request
     */
    OrangeResponse delete(final OrangeRequest request);

}
