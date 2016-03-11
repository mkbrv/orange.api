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


        public static final String ERROR = "error";
        public static final String ERROR_DESCRIPTION = "error_description";
    }


    @Override
    public OrangeException deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        OrangeException orangeException = new OrangeException();

        if (jsonObject.has(Params.CODE)) {
            this.addExceptionDetailsForCloud(orangeException, jsonObject);
        } else {
            this.addExceptionDetailsForIdentity(orangeException, jsonObject);
        }

        return orangeException;
    }

    /**
     * Different json is used for Cloud
     *
     * @param orangeException
     * @param jsonObject
     */
    private void addExceptionDetailsForCloud(final OrangeException orangeException, final JsonObject jsonObject) {
        orangeException.setCode(notNull(Params.CODE, jsonObject));
        orangeException.setName(notNull(Params.MESSAGE, jsonObject));
        orangeException.setDescription(notNull(Params.DESCRIPTION, jsonObject));
    }

    /**
     * Different json is used for identity
     *
     * @param orangeException
     * @param jsonObject
     */
    private void addExceptionDetailsForIdentity(final OrangeException orangeException, final JsonObject jsonObject) {
        orangeException.setCode(notNull(Params.ERROR, jsonObject));
        orangeException.setName(notNull(Params.ERROR, jsonObject));
        orangeException.setDescription(notNull(Params.ERROR_DESCRIPTION, jsonObject));
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
