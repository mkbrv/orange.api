package com.mkbrv.orange.cloud.response;

import com.mkbrv.orange.httpclient.response.OrangeResponse;

/**
 * Wrapper over the orange response;
 * Created by mkbrv on 08/03/16.
 */
public class GenericResponse {

    final OrangeResponse orangeResponse;

    final Boolean operationStatus;

    public GenericResponse(final OrangeResponse orangeResponse, final Boolean operationStatus) {
        this.orangeResponse = orangeResponse;
        this.operationStatus = operationStatus;
    }

    public OrangeResponse getOrangeResponse() {
        return orangeResponse;
    }

    /**
     * @return
     */
    public Boolean isOperationSuccessful() {
        return this.operationStatus;
    }
}
