package com.mkbrv.orange.cloud;

import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.impl.OrangeCloudFoldersAPIImpl;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

/**
 * Created by mikibrv on 20/02/16.
 */
public class OrangeCloudFoldersAPITests {

    private OrangeCloudFoldersAPI orangeCloudFoldersAPI;
    private OrangeHttpClient orangeHttpClient;

    protected final OrangeClientConfiguration orangeClientConfiguration
            = new OrangeClientConfiguration("appId", "clientID", "clientSecret", "http://app.com");

    @Before
    public void init() throws IOException {
        OrangeContext orangeContext = new OrangeContext().setOrangeClientConfiguration(this.orangeClientConfiguration);
        orangeHttpClient = Mockito.mock(OrangeHttpClient.class);
        orangeCloudFoldersAPI = new OrangeCloudFoldersAPIImpl(orangeContext, orangeHttpClient);
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
                setBody(readFile("rootfolder.json"));
            }
        });

        OrangeAccessToken orangeAccessToken = new OrangeAccessToken("token");
        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,null);
        assertNotNull(rootFolder);
        assertEquals("Lw", rootFolder.getParentFolderId());
        assertTrue(rootFolder.getSubFolders().size() > 0);
        assertTrue(rootFolder.getFiles().size() > 0);
        assertNotNull(rootFolder.getFiles().get(0).getCreationDate());
    }

    private String readFile(final String fileName) {
        String result = "";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
