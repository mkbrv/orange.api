package com.mkbrv.orange.client;

/**
 * Created by mikibrv on 19/02/16.
 */
public interface OrangeJsonParser<T> {

    /**
     * Turns the json into an object returned by the implmenetations
     *
     * @param json
     * @return
     */
    T parseJson(final String json);


}
