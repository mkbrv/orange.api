package com.mkbrv.orange.httpclient;

import com.mkbrv.orange.httpclient.exception.OrangeException;
import com.mkbrv.orange.httpclient.request.OrangeRequest;
import com.mkbrv.orange.httpclient.response.OrangeResponse;
import org.junit.gen5.api.Assertions;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertNotNull;

/**
 * Tests regarding http connectivity to a remote endpoint;
 * Created by mkbrv on 16/02/16.
 */
public class HttpClientConnectionTest {

    final OrangeHttpClient orangeHttpClient = new SimpleHttpClient();
    final String ORANGE_RES_STATUS = "https://api.orange.com/res/status";

    @Test @org.junit.Test
    public void simpleHttpClientCanSendARequest() {
        OrangeRequest orangeRequest = new OrangeRequest().setUrl(ORANGE_RES_STATUS);
        this.validateNonNullResponse(orangeHttpClient.doGet(orangeRequest));
    }


    @Test @org.junit.Test
    public void simpleHttpClientCanSendAPostRequest() {
        OrangeRequest orangeRequest = new OrangeRequest().setUrl(ORANGE_RES_STATUS);
        this.validateNonNullResponse(orangeHttpClient.doPost(orangeRequest));
    }

    @Test @org.junit.Test
    public void simpleHttpClientCanSendADeleteRequest() {
        OrangeRequest orangeRequest = new OrangeRequest().setUrl(ORANGE_RES_STATUS);
        this.validateNonNullResponse(orangeHttpClient.delete(orangeRequest));
    }

    private void validateNonNullResponse(final OrangeResponse orangeResponse) {
        assertNotNull(orangeResponse);
        assertNotNull(orangeResponse.getStatus());
    }


    @Test @org.junit.Test()
    public void invalidURLWillGenerateOrangeException() {
        Assertions.expectThrows(OrangeException.class,
                () -> orangeHttpClient.doGet(new OrangeRequest().setUrl("BAD_URL")));
    }

    @Test @org.junit.Test
    public void requestReturnsAResponseBody() {
        OrangeResponse orangeResponse = orangeHttpClient.doGet(new OrangeRequest().setUrl(ORANGE_RES_STATUS));
        assertNotNull(orangeResponse.getBody());
    }


}
