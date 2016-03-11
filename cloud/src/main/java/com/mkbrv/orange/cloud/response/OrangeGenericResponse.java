package com.mkbrv.orange.cloud.response;

import com.mkbrv.orange.client.response.OrangeResponse;

/**
 * Created by mkbrv on 08/03/16.
 */
public class OrangeGenericResponse {

    final Integer STATUS_REMOVED = 204;

    final OrangeResponse orangeResponse;

    public OrangeGenericResponse(final OrangeResponse orangeResponse) {
        this.orangeResponse = orangeResponse;
    }

    public OrangeResponse getOrangeResponse() {
        return orangeResponse;
    }

    public Boolean wasRemoved() {
        return orangeResponse.getStatus().equals(STATUS_REMOVED);
    }
}
