package com.mkbrv.orange.client.exception;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by mkbrv on 20/02/16.
 */
public class OrangeExceptionDeserializer implements JsonDeserializer<OrangeException> {


    public static class Params {
        public static final String CODE = "code";
        public static final String MESSAGE = "message";
        public static final String DESCRIPTION = "description";
    }


    @Override
    public OrangeException deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        OrangeException orangeException = new OrangeException();
        orangeException.setCode(notNull(Params.CODE, jsonObject));
        orangeException.setName(notNull(Params.MESSAGE, jsonObject));
        orangeException.setDescription(notNull(Params.DESCRIPTION, jsonObject));
        return orangeException;
    }

    /**
     * Prevents NPE from the json deserializer
     *
     * @param key
     * @param jsonObject
     * @return
     */
    private String notNull(final String key, final JsonObject jsonObject) {
        if (jsonObject.has(key)) {
            return jsonObject.get(key).getAsString();
        }
        return null;
    }

}
