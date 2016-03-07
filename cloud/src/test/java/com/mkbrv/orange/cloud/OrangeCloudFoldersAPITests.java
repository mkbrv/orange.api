package com.mkbrv.orange.cloud;


import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * Created by mkbrv on 20/02/16.
 */
public class OrangeCloudFoldersAPITests extends AbstractOrangeCloudAPITests {


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
