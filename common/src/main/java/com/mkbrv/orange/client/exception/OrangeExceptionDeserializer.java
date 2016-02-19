package com.mkbrv.orange.client.exception;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by mikibrv on 20/02/16.
 */
public class OrangeExceptionDeserializer implements JsonDeserializer<OrangeException> {


    public static class Params {
        public static final String ERROR = "error";
        public static final String ERROR_DESCRIPTION = "error_description";
    }


    @Override
    public OrangeException deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        OrangeException orangeException = new OrangeException();
        orangeException.setName(jsonObject.get(Params.ERROR).getAsString());
        orangeException.setDescription(jsonObject.get(Params.ERROR_DESCRIPTION).getAsString());


        return orangeException;
    }
}
