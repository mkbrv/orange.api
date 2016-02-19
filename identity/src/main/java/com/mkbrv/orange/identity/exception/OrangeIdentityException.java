package com.mkbrv.orange.identity.exception;

import com.mkbrv.orange.client.exception.OrangeException;

/**
 * Created by mikibrv on 17/02/16.
 */
public class OrangeIdentityException extends OrangeException {


    public OrangeIdentityException(final String message) {
        this.setName(message);
    }
}
