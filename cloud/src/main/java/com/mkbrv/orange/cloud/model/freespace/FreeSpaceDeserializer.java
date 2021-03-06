package com.mkbrv.orange.cloud.model.freespace;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mkbrv.orange.cloud.util.JsonTranslator;

import java.lang.reflect.Type;

/**
 * Created by mkbrv on 20/02/16.
 */
public class FreeSpaceDeserializer implements JsonDeserializer<OrangeFreeSpace>, JsonTranslator {

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
