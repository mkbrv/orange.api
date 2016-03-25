package com.mkbrv.orange.httpclient;

import com.mkbrv.orange.httpclient.exception.OrangeException;
import com.mkbrv.orange.httpclient.request.OrangeRequest;
import com.mkbrv.orange.httpclient.response.OrangeResponse;
import com.mkbrv.orange.httpclient.security.OrangeAccessTokenHeader;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by mkbrv on 16/02/16.
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
        HttpGet httpGet = new HttpGet(this.buildUrlWithParameters(request.getUrl(), request.getParameters()));
        this.addAuthParameterToHeaderIfRequired(request);
        request.getHeaders().forEach(httpGet::addHeader);
        return executeRequestAndReadResponse(httpGet).setOrangeRequest(request);
    }

    /**
     * Builds the URL with parameters (for get)
     *
     * @param url        base URL
     * @param parameters parameters to be appended
     * @return url with parameters
     */
    private String buildUrlWithParameters(final String url, final Map<String, String> parameters) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            parameters.forEach(uriBuilder::addParameter);
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            LOG.warn(e.getMessage(), e);
            return url;
        }
    }

    /**
     * @param request
     */
    public OrangeResponse doPost(final OrangeRequest request) {
        HttpPost httpPost = new HttpPost(request.getUrl());
        request.getHeaders().forEach(httpPost::addHeader);
        this.addAuthParameterToHeaderIfRequired(request);
        if (request.hasContent()) {
            this.addContentJSONBody(request.getBody(), httpPost);
        } else {
            this.addHttpParamsToBody(request, httpPost);
        }
        return executeRequestAndReadResponse(httpPost).setOrangeRequest(request);
    }

    /**
     * @param body
     * @param httpPost
     */
    protected void addContentJSONBody(final String body, final HttpPost httpPost) {
        StringEntity input = null;
        try {
            input = new StringEntity(body);
            input.setContentType(ContentType.APPLICATION_JSON.toString());
            httpPost.setEntity(input);
        } catch (UnsupportedEncodingException e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    protected void addHttpParamsToBody(final OrangeRequest orangeRequest, final HttpPost httpPost) {
        try {
            List<NameValuePair> requestParameters = new ArrayList<>();
            orangeRequest.getParameters().forEach((key, value) -> requestParameters.add(new BasicNameValuePair(key, value)));
            httpPost.setEntity(new UrlEncodedFormEntity(requestParameters));
        } catch (UnsupportedEncodingException e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    /**
     * @param request
     */
    public OrangeResponse delete(final OrangeRequest request) {
        HttpDelete httpDelete = new HttpDelete(request.getUrl());
        this.addAuthParameterToHeaderIfRequired(request);
        request.getHeaders().forEach(httpDelete::addHeader);
        return executeRequestAndReadResponse(httpDelete).setOrangeRequest(request);
    }

    @Override
    public InputStream downloadFile(OrangeRequest request) throws IOException {
        HttpGet httpGet = new HttpGet(request.getUrl());
        this.addAuthParameterToHeaderIfRequired(request);
        request.getHeaders().forEach(httpGet::addHeader);
        return this.client.execute(httpGet).getEntity().getContent();
    }

    /**
     * @param orangeRequest
     */
    protected void addAuthParameterToHeaderIfRequired(final OrangeRequest orangeRequest) {
        if (orangeRequest.getHeaders().containsKey(OrangeAccessTokenHeader.HEADER_AUTHORIZATION)) {
            return;
        } else if (orangeRequest.getOrangeAccessToken() != null &&
                orangeRequest.getOrangeAccessToken().getToken() != null) {
            orangeRequest.addHeader(OrangeAccessTokenHeader.HEADER_AUTHORIZATION,
                    OrangeAccessTokenHeader.BEARER + " " + orangeRequest.getOrangeAccessToken().getToken());
        }
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
        LOG.info("Called URL : {} with response : {}", httpRequest.getURI(), orangeResponse.getBody());
        return orangeResponse;
    }

    /**
     * Reads the response from the stream into a string;
     *
     * @param response
     * @return
     */
    protected String readResponseBody(final HttpResponse response) {
        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()))) {
            return buffer.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new OrangeException(e);
        }
    }


    /**
     * Executes a request using the http httpclient.
     *
     * @param httpRequest request to be executed
     * @return HttpResponse
     * @throws OrangeException in case there was an issue either on the httpclient or on the server
     */
    protected HttpResponse executeRequest(final HttpRequestBase httpRequest) {
        try {
            return client.execute(httpRequest);
        } catch (IOException e) {
            throw new OrangeException(e);
        }
    }
}
