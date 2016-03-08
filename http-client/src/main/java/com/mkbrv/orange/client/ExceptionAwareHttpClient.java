package com.mkbrv.orange.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.exception.OrangeExceptionDeserializer;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decorator for the http client. aware of orange errors
 * Throws exceptions based on orange errors
 * Created by mkbrv on 19/02/16.
 */
public class ExceptionAwareHttpClient implements OrangeHttpClient {

    private final Logger LOG = LoggerFactory.getLogger(ExceptionAwareHttpClient.class);

    private final OrangeHttpClient actualClient;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(OrangeException.class,
            new OrangeExceptionDeserializer()).create();


    public ExceptionAwareHttpClient(final OrangeHttpClient orangeHttpClient) {
        this.actualClient = orangeHttpClient;
    }

    @Override
    public OrangeResponse doGet(final OrangeRequest request) {
        return this.verifyResponse(this.actualClient.doGet(request));
    }

    @Override
    public OrangeResponse doPost(final OrangeRequest request) {
        return this.verifyResponse(this.actualClient.doPost(request));
    }

    @Override
    public OrangeResponse delete(final OrangeRequest request) {
        return this.verifyResponse(this.actualClient.delete(request));
    }


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
     * @param orangeResponse
     * @return
     */
    private OrangeException attemptToParseException(final OrangeResponse orangeResponse) {
        OrangeException orangeException;
        try {
            orangeException = gson.fromJson(orangeResponse.getBody().toString(), OrangeException.class)
                    .setOrangeResponse(orangeResponse);
            LOG.error(orangeResponse.getOrangeRequest() + orangeResponse.getBody().toString());
        } catch (Exception e) {
            orangeException = new OrangeException(e)
                    .setCode(orangeResponse.getStatus())
                    .setDescription(orangeResponse.getBody().toString())
                    .setOrangeResponse(orangeResponse);
        }
        return orangeException;
    }

    private enum Status {
        OK,
        NOT_OK
    }

    private Status computeStatusBasedOnValue(Integer httpStatus) {
        if (httpStatus != null && httpStatus < 300 && httpStatus >= 200) {
            return Status.OK;
        }
        return Status.NOT_OK;
    }
}
