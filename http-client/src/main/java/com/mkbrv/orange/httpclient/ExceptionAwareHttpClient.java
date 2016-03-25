package com.mkbrv.orange.httpclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mkbrv.orange.httpclient.exception.OrangeException;
import com.mkbrv.orange.httpclient.exception.OrangeExceptionDeserializer;
import com.mkbrv.orange.httpclient.request.OrangeRequest;
import com.mkbrv.orange.httpclient.response.OrangeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decorator for the http httpclient. aware of orange errors (can parse them)
 * Throws exceptions based on orange errors
 * Created by mkbrv on 19/02/16.
 */
public class ExceptionAwareHttpClient implements OrangeHttpClient {

    private final Logger LOG = LoggerFactory.getLogger(ExceptionAwareHttpClient.class);

    private final OrangeHttpClient actualClient;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(OrangeException.class,
            new OrangeExceptionDeserializer()).create();

    /**
     * @param orangeHttpClient decorated httpclient
     */
    public ExceptionAwareHttpClient(final OrangeHttpClient orangeHttpClient) {
        this.actualClient = orangeHttpClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeResponse doGet(final OrangeRequest request) {
        return this.verifyResponse(this.actualClient.doGet(request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeResponse doPost(final OrangeRequest request) {
        return this.verifyResponse(this.actualClient.doPost(request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeResponse delete(final OrangeRequest request) {
        return this.verifyResponse(this.actualClient.delete(request));
    }

    @Override
    public InputStream downloadFile(OrangeRequest request) throws IOException {
        return this.actualClient.downloadFile(request);
    }

    /**
     * The orangeResponse is verified for eventual errors which can be parsed;
     *
     * @param orangeResponse orangeResponse
     * @return orangeResponse unaltered
     */
    private OrangeResponse verifyResponse(final OrangeResponse orangeResponse) {
        switch (this.computeStatusBasedOnValue(orangeResponse.getStatus())) {
            case OK:
                return orangeResponse;
            case NOT_OK:
            default:
                throw attemptToParseException(orangeResponse);
        }
    }

    /**
     * Will attempt to parse the exception message from orange. if is unable to do it, will throw a generic exception
     *
     * @param orangeResponse contains the body json
     * @return OrangeException from the json
     */
    protected OrangeException attemptToParseException(final OrangeResponse orangeResponse) {
        OrangeException orangeException;
        try {
            orangeException = gson.fromJson(orangeResponse.getBody().toString(), OrangeException.class)
                    .setOrangeResponse(orangeResponse);
            LOG.error(orangeResponse.getOrangeRequest() + orangeResponse.getBody().toString());
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
            orangeException = new OrangeException(e)
                    .setCode(orangeResponse.getStatus())
                    .setDescription(orangeResponse.getBody().toString())
                    .setOrangeResponse(orangeResponse);
        }
        return orangeException;
    }

    /**
     * used for the switch regarding if the response of the api call was OK or not
     */
    protected enum Status {
        OK,
        NOT_OK
    }

    /**
     * Returns if the status is ok.
     *
     * @param httpStatus value of the http response status
     * @return OK for a status of type 2xx
     * NOT_OK for other statuses;
     */
    protected Status computeStatusBasedOnValue(Integer httpStatus) {
        if (httpStatus != null && httpStatus < 300 && httpStatus >= 200) {
            return Status.OK;
        }
        return Status.NOT_OK;
    }
}
