package com.mkbrv.orange.cloud.model.folder;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mkbrv.orange.cloud.model.OrangeFile;

import java.lang.reflect.Type;

/**
 * Created by mkbrv on 26/02/16.
 */
public class OrangeFolderDeserializer implements JsonDeserializer<DefaultOrangeFolder> {

    public static class Params {
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String PARENTID = "parentId";
        public static final String SUBFOLDERS = "subfolders";
        public static final String FILES = "files";
    }


    @Override
    public DefaultOrangeFolder deserialize(final JsonElement jsonElement,
                                           final Type type, final JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        DefaultOrangeFolder orangeFolder = new DefaultOrangeFolder(jsonObject.get(Params.ID).getAsString());

        orangeFolder.setName(jsonObject.get(Params.NAME).getAsString());
        orangeFolder.setParentFolderId(jsonObject.get(Params.PARENTID).getAsString());

        JsonArray subFoldersArray = jsonObject.getAsJsonArray(Params.SUBFOLDERS);
        this.addSubFolders(orangeFolder, subFoldersArray);
        JsonArray filesArray = jsonObject.getAsJsonArray(Params.FILES);
        this.addFiles(orangeFolder, filesArray, jsonDeserializationContext);

        return orangeFolder;
    }

    /**
     * @param orangeFolder
     * @param filesArray
     * @param jsonDeserializationContext
     */
    private void addFiles(final DefaultOrangeFolder orangeFolder, final JsonArray filesArray,
                          final JsonDeserializationContext jsonDeserializationContext) {
        for (int index = 0; index < filesArray.size(); index++) {
            JsonElement fileJson = filesArray.get(index);
            OrangeFile orangeFile = jsonDeserializationContext.deserialize(fileJson, OrangeFile.class);
            orangeFolder.addFile(orangeFile);
        }
    }

    /**
     * @param orangeFolder
     * @param subFoldersArray
     */
    private void addSubFolders(final DefaultOrangeFolder orangeFolder, final JsonArray subFoldersArray) {
        for (int index = 0; index < subFoldersArray.size(); index++) {
            JsonObject subFolderJson = subFoldersArray.get(index).getAsJsonObject();
            DefaultOrangeFolder subFolder = new DefaultOrangeFolder(subFolderJson.get(Params.ID).getAsString())
                    .setName(subFolderJson.get(Params.NAME).getAsString())
                    .setParentFolderId(subFolderJson.get(Params.PARENTID).getAsString());
            orangeFolder.addSubFolder(subFolder);
        }
    }
}
