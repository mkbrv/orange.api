package com.mkbrv.orange.client;


import com.mkbrv.orange.client.request.OrangeRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Created by mkbrv on 03/03/16.
 */
public class HttpClientParametersTests {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientParametersTests.class);

    SimpleHttpClient simpleHttpClient;

    HttpResponse response;

    @Before
    public void init() {
        this.simpleHttpClient = Mockito.spy(new SimpleHttpClient());
        this.response = Mockito.mock(HttpResponse.class);
        //prevent npe
        when(this.response.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("GET", 1, 1), 200, "reason"));
        doReturn("{}").when(this.simpleHttpClient).readResponseBody(any());
    }

    Map<String, Object> TEST_PARAMETERS = new HashMap<String, Object>() {{
        put("key", "value");
        put("key2", null);
        put("key3", Boolean.TRUE);
        put("key4", 10);
        put("key5", 10L);
        put("key6", 10D);
    }};


    @Test
    public void getRequestHasParametersInURL() {

        doAnswer(invocationOnMock -> {
            //we have to check for each of the tested parameters to be present (and values)
            HttpRequestBase httpRequest = (HttpRequestBase) invocationOnMock.getArguments()[0];
            String urlQuery = httpRequest.getURI().getQuery();
            assertNotNull(urlQuery);
            TEST_PARAMETERS.forEach((key, value) -> {
                if (value != null) {
                    assertTrue(urlQuery.contains(key + "=" + value));
                } else {
                    assertTrue(urlQuery.contains(key));
                }
            });

            LOG.info("Resulted URL Query : {}", urlQuery);

            return response;
        }).when(simpleHttpClient).executeRequest(any());

        OrangeRequest orangeRequest = new OrangeRequest().setUrl("http://orange.com");
        TEST_PARAMETERS.forEach(orangeRequest::addParameter);
        simpleHttpClient.doGet(orangeRequest);

    }

}
