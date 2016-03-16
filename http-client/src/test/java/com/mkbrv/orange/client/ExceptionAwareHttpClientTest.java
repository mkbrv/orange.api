package com.mkbrv.orange.client;

import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import org.junit.gen5.api.Assertions;
import org.junit.gen5.api.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by mkbrv on 19/02/16.
 */
public class ExceptionAwareHttpClientTest {


    @Test()
    public void httpErrorStatusThrowsException() {

        OrangeHttpClient orangeHttpClient = Mockito.mock(OrangeHttpClient.class);
        when(orangeHttpClient.doGet(any())).thenReturn(new OrangeResponse() {
            {
                setBody("{}");
                setStatus(400);
            }
        });

        OrangeHttpClient errorAwareClient = new ExceptionAwareHttpClient(orangeHttpClient);
        Assertions.expectThrows(OrangeException.class, () -> errorAwareClient.doGet(new OrangeRequest()));
    }


}
