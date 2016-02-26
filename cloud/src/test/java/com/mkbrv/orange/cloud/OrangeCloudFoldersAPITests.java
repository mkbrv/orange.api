package com.mkbrv.orange.cloud;

import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.impl.OrangeCloudFoldersAPIImpl;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

}
