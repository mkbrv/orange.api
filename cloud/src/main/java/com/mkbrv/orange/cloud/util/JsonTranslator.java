package com.mkbrv.orange.cloud.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;

/**
 * Created by mkbrv on 16/03/16.
 */
public interface JsonTranslator {

    /**
     * @param date
     * @return
     */
    default Date parseDate(final JsonElement date, final JsonDeserializationContext jsonDeserializationContext) {
        if (date == null) {
            return null;
        }
        return jsonDeserializationContext.deserialize(date, Date.class);
    }


    /**
     * Prevents NPE from the json deserializer
     *
     * @param key
     * @param jsonObject
     * @return
     */
    default String notNull(final String key, final JsonObject jsonObject) {
        if (jsonObject.has(key)) {
            return jsonObject.get(key).getAsString();
        }
        return null;
    }

    /**
     * Adds string to json object parameters if not null;
     *
     * @param jsonObject
     * @param key
     * @param value
     */
    default void addStringParamIfNotNull(final JsonObject jsonObject, final String key, final String value) {
        if (value != null) {
            jsonObject.addProperty(key, value);
        }
    }


}
