package com.mkbrv.orange.client;

import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
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

    @Test
    public void simpleHttpClientCanSendARequest() {
        OrangeRequest orangeRequest = new OrangeRequest().setUrl(ORANGE_RES_STATUS);
        this.validateNonNullResponse(orangeHttpClient.doGet(orangeRequest));
    }


    @Test
    public void simpleHttpClientCanSendAPostRequest() {
        OrangeRequest orangeRequest = new OrangeRequest().setUrl(ORANGE_RES_STATUS);
        this.validateNonNullResponse(orangeHttpClient.doPost(orangeRequest));
    }

    @Test
    public void simpleHttpClientCanSendADeleteRequest() {
        OrangeRequest orangeRequest = new OrangeRequest().setUrl(ORANGE_RES_STATUS);
        this.validateNonNullResponse(orangeHttpClient.delete(orangeRequest));
    }

    private void validateNonNullResponse(final OrangeResponse orangeResponse) {
        assertNotNull(orangeResponse);
        assertNotNull(orangeResponse.getStatus());
    }


    @Test()
    public void invalidURLWillGenerateOrangeException() {
        Assertions.expectThrows(OrangeException.class,
                () -> orangeHttpClient.doGet(new OrangeRequest().setUrl("BAD_URL")));
    }

    @Test
    public void requestReturnsAResponseBody() {
        OrangeResponse orangeResponse = orangeHttpClient.doGet(new OrangeRequest().setUrl(ORANGE_RES_STATUS));
        assertNotNull(orangeResponse.getBody());
    }


}
