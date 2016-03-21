package com.mkbrv.orange.cloud.model.folder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.util.JsonTranslator;

import java.lang.reflect.Type;

/**
 * Currently used only for create folder
 * Created by mkbrv on 16/03/16.
 */
public class FolderSerializer implements JsonSerializer<OrangeFolder>, JsonTranslator {

    public static class Params {
        public static final String PARENT_FOLDER_ID = "parentFolderId";
        public static final String NAME = "name";

    }


    @Override
    public JsonElement serialize(final OrangeFolder orangeFolder,
                                 final Type type,
                                 final JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonElement = new JsonObject();
        this.addStringParamIfNotNull(jsonElement, Params.PARENT_FOLDER_ID, orangeFolder.getParentFolderId());
        this.addStringParamIfNotNull(jsonElement, Params.NAME, orangeFolder.getParentFolderId());
        return jsonElement;
    }
}
