package com.mkbrv.orange.cloud.request;

import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.folder.DefaultOrangeFolder;
import org.junit.Test;

import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertTrue;

/**
 * Created by mkbrv on 25/03/16.
 */
public class UpdateRequestBodyTest {

    final UpdateRequestBody updateRequestBody = new UpdateRequestBody();

    @Test
    @org.junit.gen5.api.Test
    public void renameFolderRequestBodyTest() {
        OrangeFolder folderToUpdate = new DefaultOrangeFolder().setName("the new name");
        String actual = updateRequestBody.buildRenameRequestBody(folderToUpdate.getName());
        assertTrue(actual.contains(folderToUpdate.getName()));
    }

    @org.junit.gen5.api.Test
    @Test
    public void copyFolderRequestBodyTest() {
        OrangeFolder newParentFolder = new DefaultOrangeFolder("someId");
        String actual = updateRequestBody.buildCopyRequestBody(newParentFolder);
        assertTrue(actual.contains("clone"));
        assertTrue(actual.contains(newParentFolder.getId()));
    }

    @org.junit.gen5.api.Test
    @Test
    public void moveFolderRequestBodyTest() {
        OrangeFolder newParentFolder = new DefaultOrangeFolder("someId");
        String actual = updateRequestBody.buildMoveRequestBody(newParentFolder);
        assertFalse(actual.contains("clone"));
        assertTrue(actual.contains(newParentFolder.getId()));

    }
}
