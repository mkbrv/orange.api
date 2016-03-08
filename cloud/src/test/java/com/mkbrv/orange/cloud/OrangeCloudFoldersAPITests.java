package com.mkbrv.orange.cloud;


import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import com.mkbrv.orange.cloud.response.OrangeDeleteFolderResponse;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * Created by mkbrv on 20/02/16.
 */
public class OrangeCloudFoldersAPITests extends AbstractOrangeCloudAPITests {

    @Test
    public void removeFolderFailsDueToException() {
        Mockito.when(orangeHttpClient.delete(any())).thenReturn(new OrangeResponse() {
            {
                setStatus(400);
                setBody("{\n" +
                        "  \"code\": 800,\n" +
                        "  \"message\": \"PDK_CW_0002-INVALID_PARAMETER\",\n" +
                        "  \"description\": \"Invalid some parameter\",\n" +
                        "  \"infoURL\": \"http://orange.com\"\n" +
                        "}");
            }
        });

        OrangeAccessToken orangeAccessToken = new OrangeAccessToken("token");
        OrangeDeleteFolderResponse response = orangeCloudFoldersAPI.deleteFolder(orangeAccessToken,
                new OrangeFolder("random"));
        assertNotNull(response);
        assertFalse(response.wasRemoved());
    }

    @Test
    public void canRemoveOneFolder() {
        Mockito.when(orangeHttpClient.delete(any())).thenReturn(new OrangeResponse() {
            {
                setStatus(204);
                setBody("");
            }
        });

        OrangeAccessToken orangeAccessToken = new OrangeAccessToken("token");
        OrangeDeleteFolderResponse response = orangeCloudFoldersAPI.deleteFolder(orangeAccessToken,
                new OrangeFolder("random"));
        assertNotNull(response);
        assertTrue(response.wasRemoved());
    }


    @Test
    public void canGetOneFolder() {
        Mockito.when(orangeHttpClient.doGet(any())).thenReturn(new OrangeResponse() {
            {
                setStatus(200);
                setBody(readValidResponseBody("folder.json"));
            }
        });

        OrangeAccessToken orangeAccessToken = new OrangeAccessToken("token");
        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                new OrangeFolder("random"), null);
        assertNotNull(orangeFolder);
        assertNotNull(orangeFolder.getParentFolderId());
        assertTrue(orangeFolder.getSubFolders().size() > 0);
        assertTrue(orangeFolder.getFiles().size() > 0);
        assertNotNull(orangeFolder.getFiles().get(0).getCreationDate());
    }


    @Test
    public void canGetFreeSpaceAvailable() {
        final Long expectedSize = 200L;
        Mockito.when(orangeHttpClient.doGet(any())).thenReturn(new OrangeResponse() {
            {
                setStatus(200);
                setBody("{\n" +
                        "  \"freespace\": " + expectedSize + "\n" +
                        "}");
            }
        });

        OrangeAccessToken orangeAccessToken = new OrangeAccessToken("token");
        OrangeFreeSpace freeSpace = orangeCloudFoldersAPI.getAvailableSpace(orangeAccessToken);
        assertNotNull(freeSpace);
        assertEquals(expectedSize, freeSpace.getAvailableSpace());
    }


    @Test
    public void canGetRootFolder() {
        Mockito.when(orangeHttpClient.doGet(any())).thenReturn(new OrangeResponse() {
            {
                setStatus(200);
                setBody(readValidResponseBody("rootfolder.json"));
            }
        });

        OrangeAccessToken orangeAccessToken = new OrangeAccessToken("token");
        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken, null);
        assertNotNull(rootFolder);
        assertEquals("Lw", rootFolder.getParentFolderId());
        assertTrue(rootFolder.getSubFolders().size() > 0);
        assertTrue(rootFolder.getFiles().size() > 0);
        assertNotNull(rootFolder.getFiles().get(0).getCreationDate());
    }


}
