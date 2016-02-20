package com.mkbrv.orange.cloud.model;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by mikibrv on 20/02/16.
 */
public class OrangeFreeSpaceDeserializer implements JsonDeserializer<OrangeFreeSpace> {

    public static class Params {
        public static final String FREESPACE = "freespace";
    }


    @Override
    public OrangeFreeSpace deserialize(JsonElement jsonElement,
                                       Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Long freeSpace = jsonObject.get(Params.FREESPACE).getAsLong();
        return new OrangeFreeSpace(freeSpace);
    }
}
