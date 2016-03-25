package com.mkbrv.orange.httpclient.exception;

import com.mkbrv.orange.httpclient.response.OrangeResponse;

/**
 * Created by mkbrv on 16/02/16.
 */
public class OrangeException extends RuntimeException {

    private String code;

    private String name;

    private String description;

    private OrangeResponse orangeResponse;

    /**
     * If this is a httpclient exception (and not from Orange)
     */
    private final Exception clientException;

    public OrangeException() {
        this.clientException = null;
    }

    public OrangeException(final String message, final Exception clientException) {
        this.clientException = clientException;
        this.setName(message);
    }


    public OrangeException(final Exception clientException) {
        this.clientException = clientException;
    }

    public String getCode() {
        return code;
    }

    public OrangeException setCode(String code) {
        this.code = code;
        return this;
    }

    public OrangeException setCode(Integer code) {
        this.code = String.valueOf(code);
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

    public OrangeException setOrangeResponse(OrangeResponse orangeResponse) {
        this.orangeResponse = orangeResponse;
        return this;
    }

    public OrangeResponse getOrangeResponse() {
        return orangeResponse;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrangeException that = (OrangeException) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return clientException != null ? clientException.equals(that.clientException) : that.clientException == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (clientException != null ? clientException.hashCode() : 0);
        return result;
    }
}
