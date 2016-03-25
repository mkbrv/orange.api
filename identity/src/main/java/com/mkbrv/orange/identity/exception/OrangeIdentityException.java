package com.mkbrv.orange.identity.exception;

import com.mkbrv.orange.httpclient.exception.OrangeException;

/**
 * Created by mkbrv on 17/02/16.
 */
public class OrangeIdentityException extends OrangeException {


    public OrangeIdentityException(final String message) {
        this.setName(message);
    }
}
