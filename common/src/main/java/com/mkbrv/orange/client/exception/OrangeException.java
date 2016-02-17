package com.mkbrv.orange.client.exception;

/**
 * Created by mikibrv on 16/02/16.
 */
public class OrangeException extends RuntimeException {

    private Integer code;

    private String name;

    private String message;

    private String description;

    /**
     * If this is a client exception (and not from Orange)
     */
    private final Exception clientException;

    public OrangeException() {
        this.clientException = null;
    }

    public OrangeException(final Exception clientException) {
        this.clientException = clientException;
    }

    public Integer getCode() {
        return code;
    }

    public OrangeException setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrangeException setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public OrangeException setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OrangeException setDescription(String description) {
        this.description = description;
        return this;
    }

    public Exception getClientException() {
        return clientException;
    }
}
