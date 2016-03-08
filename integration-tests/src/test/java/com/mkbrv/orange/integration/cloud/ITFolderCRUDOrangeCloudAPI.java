package com.mkbrv.orange.integration.cloud;

import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.impl.OrangeCloudFoldersAPIImpl;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.request.OrangeFolderFilterParams;
import com.mkbrv.orange.cloud.request.OrangeFolderRequestParams;
import com.mkbrv.orange.cloud.response.OrangeDeleteFolderResponse;
import com.mkbrv.orange.integration.identity.ITOrangeIdentityAPI;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by mkbrv on 08/03/16.
 */
public class ITFolderCRUDOrangeCloudAPI extends ITOrangeIdentityAPI {
    private static final Logger LOG = LoggerFactory.getLogger(ITRootFolderCloudAPI.class);
    OrangeCloudFoldersAPI orangeCloudFoldersAPI;

    @Before
    public void init() throws IOException {
        super.init();
        orangeCloudFoldersAPI = new OrangeCloudFoldersAPIImpl(this.orangeContext);
    }


    @Test
    public void canCreateOrangeFolder() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        final String name = "testName";
        OrangeFolder orangeFolder = orangeCloudFoldersAPI.createFolder(orangeAccessToken,
                new OrangeFolderRequestParams().setName(name)
                        .setParentFolderId("X191cGxvYWQvZGUgRVRJRU5ORS1BU1VTL2RlIG1hIGJvaXRlIGTigJllbnZvaS9tb3JlIHRoYW4gMTAwMCBwaWNzLw"));
        assertNotNull(orangeFolder);
        assertEquals(name, orangeFolder.getName());

        //check if we can retrieve it
        OrangeFolder testRetrieval = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                orangeFolder, null);
        assertNotNull(testRetrieval);
        assertEquals(orangeFolder, testRetrieval);
    }

    /**
     * tricky and problematic test.
     * It requires extra permissions and it is not transactional.
     */
    @Test
    public void canRemoveFolderFromOrange() {
        //for this test I would rather not risk actually deleting folders, since there is no revert possible;
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        final String orangeFolderToRemove = this.properties.getProperty("orange.folder.to.remove");
        if (orangeFolderToRemove == null) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                new OrangeFolder(orangeFolderToRemove),
                new OrangeFolderFilterParams().setShowThumbnails(""));
        if (orangeFolder != null) {
            try {
                OrangeDeleteFolderResponse deleteFolderResponse =
                        orangeCloudFoldersAPI.deleteFolder(orangeAccessToken, orangeFolder);
                assertNotNull(deleteFolderResponse);
                assertTrue(deleteFolderResponse.wasRemoved());
            } catch (OrangeException orangeException) {
                //you can only get cloudfullwrite if you contact orange
                assertEquals("800", orangeException.getCode());
                assertEquals("PDK_CW_0003-FORBIDDEN_ACCESS", orangeException.getName());
            }
        }
    }

    @Test
    public void canUpdateOrangeFolder() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        final String newName = "dossierNouveau";

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        OrangeFolder orangeRootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OrangeFolderFilterParams().setRestrictedMode(""));

        //let's update the first folder
        if (orangeRootFolder.getSubFolders().size() == 0) {
            return;
        }
        OrangeFolder folderToUpdate = orangeRootFolder.getSubFolders().get(0);

        try {
            orangeCloudFoldersAPI.updateFolder(orangeAccessToken, folderToUpdate, new OrangeFolderRequestParams()
                    .setName(newName));

            orangeRootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                    new OrangeFolderFilterParams().setRestrictedMode(""));

            assertEquals(newName, orangeRootFolder.getSubFolders().get(0).getName());
        } catch (OrangeException orangeException) {
            //you can only get cloudfullwrite if you contact orange
            assertEquals(new Integer(401), orangeException.getOrangeResponse().getStatus());
        }
    }

}
