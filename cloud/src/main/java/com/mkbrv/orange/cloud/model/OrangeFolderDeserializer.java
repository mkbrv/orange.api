package com.mkbrv.orange.cloud.model;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by mikibrv on 26/02/16.
 */
public class OrangeFolderDeserializer implements JsonDeserializer<OrangeFolder> {

    public static class Params {
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String PARENTID = "parentId";
        public static final String SUBFOLDERS = "subfolders";
        public static final String FILES = "files";
    }


    @Override
    public OrangeFolder deserialize(final JsonElement jsonElement,
                                    final Type type, final JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        OrangeFolder orangeFolder = new OrangeFolder();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        orangeFolder.setName(jsonObject.get(Params.NAME).getAsString());
        orangeFolder.setId(jsonObject.get(Params.ID).getAsString());
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
    private void addFiles(final OrangeFolder orangeFolder, final JsonArray filesArray,
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
    private void addSubFolders(final OrangeFolder orangeFolder, final JsonArray subFoldersArray) {
        for (int index = 0; index < subFoldersArray.size(); index++) {
            JsonObject subFolderJson = subFoldersArray.get(index).getAsJsonObject();
            OrangeFolder subFolder = new OrangeFolder()
                    .setName(subFolderJson.get(Params.NAME).getAsString())
                    .setId(subFolderJson.get(Params.ID).getAsString())
                    .setParentFolderId(subFolderJson.get(Params.PARENTID).getAsString());
            orangeFolder.addSubFolder(subFolder);
        }
    }
}
