package com.mkbrv.orange.cloud.request;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mkbrv.orange.cloud.model.OrangeFolder;

/**
 * Helps building the json body for update/move/rename of files and folders
 * Created by mkbrv on 25/03/16.
 */
public class UpdateRequestBody {

    private static final String NAME = "name";
    private static final String CLONE = "clone";
    private static final String PARENT_FOLDER_ID = "parentFolderId";

    /**
     * @param newParentFolder
     * @return
     */
    public String buildMoveRequestBody(final OrangeFolder newParentFolder) {
        JsonObject requestBody = new JsonObject();
        requestBody.add(PARENT_FOLDER_ID, new JsonPrimitive(newParentFolder.getId()));
        return requestBody.toString();
    }

    /**
     * @param newParentFolder
     * @return
     */
    public String buildCopyRequestBody(final OrangeFolder newParentFolder) {
        JsonObject requestBody = new JsonObject();
        requestBody.add(CLONE, new JsonPrimitive(Boolean.TRUE));
        requestBody.add(PARENT_FOLDER_ID, new JsonPrimitive(newParentFolder.getId()));
        return requestBody.toString();
    }

    /**
     * Returns the body of the rename folder request;
     *
     * @param name
     * @return
     */
    public String buildRenameRequestBody(final String name) {
        JsonObject requestBody = new JsonObject();
        requestBody.add(NAME, new JsonPrimitive(name));
        return requestBody.toString();
    }
}
