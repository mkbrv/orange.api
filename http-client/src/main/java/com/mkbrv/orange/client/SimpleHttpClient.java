package com.mkbrv.orange.client;

import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mikibrv on 16/02/16.
 */
public class SimpleHttpClient implements OrangeHttpClient {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SimpleHttpClient.class);

    /**
     * Actual HttpClient;
     */
    final HttpClient client = HttpClientBuilder.create().build();

    /**
     * Performs a GET request to a specific URL
     *
     * @param request
     */
    public OrangeResponse doGet(final OrangeRequest request) {
        HttpGet httpGet = new HttpGet(request.getUrl());
        request.getHeaders().forEach(httpGet::addHeader);
        return executeRequestAndReadResponse(httpGet);
    }

    /**
     * @param request
     */
    public OrangeResponse doPost(final OrangeRequest request) {
        HttpPost httpPost = new HttpPost(request.getUrl());
        request.getHeaders().forEach(httpPost::addHeader);

        try {
            List<NameValuePair> requestParameters = new ArrayList<>();
            request.getParameters().forEach((key, value) -> requestParameters.add(new BasicNameValuePair(key, value)));
            httpPost.setEntity(new UrlEncodedFormEntity(requestParameters));
        } catch (UnsupportedEncodingException e) {
            LOG.warn(e.getMessage());
        }


        return executeRequestAndReadResponse(httpPost);
    }

    /**
     * @param request
     */
    public OrangeResponse delete(final OrangeRequest request) {
        HttpDelete httpDelete = new HttpDelete(request.getUrl());
        request.getHeaders().forEach(httpDelete::addHeader);
        return executeRequestAndReadResponse(httpDelete);
    }


    /**
     * Executes the request and also retrieves the result body and status
     *
     * @param httpRequest
     * @return
     */
    protected OrangeResponse executeRequestAndReadResponse(final HttpRequestBase httpRequest) {
        OrangeResponse orangeResponse = new OrangeResponse();
        HttpResponse response = this.executeRequest(httpRequest);
        orangeResponse.setStatus(response.getStatusLine().getStatusCode());
        orangeResponse.setBody(this.readResponseBody(response));
        return orangeResponse;
    }

    /**
     * Reads the response from the stream into a string;
     *
     * @param response
     * @return
     */
    private String readResponseBody(final HttpResponse response) {
        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()))) {
            return buffer.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new OrangeException(e);
        }
    }


    /**
     * Executes a request using the http client.
     *
     * @param httpRequest request to be executed
     * @return HttpResponse
     * @throws OrangeException in case there was an issue either on the client or on the server
     */
    protected HttpResponse executeRequest(final HttpRequestBase httpRequest) {
        try {
            return client.execute(httpRequest);
        } catch (IOException e) {
            throw new OrangeException(e);
        }
    }
}
