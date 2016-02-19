package com.mkbrv.orange.client.exception;

/**
 * Created by mikibrv on 16/02/16.
 */
public class OrangeException extends RuntimeException {

    private Integer code;

    private String name;

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
        return this.getName() + ":" + this.getDescription();
    }


    public String getDescription() {
        return description;
    }

    public OrangeException setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "OrangeException{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", clientException=" + clientException +
                '}';
    }

    public Exception getClientException() {
        return clientException;
    }
}
