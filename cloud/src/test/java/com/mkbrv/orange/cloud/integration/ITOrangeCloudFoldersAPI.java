package com.mkbrv.orange.cloud.integration;

import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.client.security.OrangeRefreshToken;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.impl.OrangeCloudFoldersAPIImpl;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import com.mkbrv.orange.identity.integration.ITOrangeIdentityAPI;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by mikibrv on 20/02/16.
 */
public class ITOrangeCloudFoldersAPI extends ITOrangeIdentityAPI {

    OrangeCloudFoldersAPI orangeCloudFoldersAPI;

    @Before
    public void init() throws IOException {
        super.init();
        orangeCloudFoldersAPI = new OrangeCloudFoldersAPIImpl(this.orangeContext);
    }


    @Test
    public void getAvailableFreeSpace() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeRefreshToken orangeRefreshToken = new OrangeRefreshToken(this.orangeAccountRefreshToken);
        OrangeAccessToken orangeAccessToken = orangeIdentityAPI.generateAccessTokenFromRefreshToken(orangeRefreshToken);

        assertNotNull(orangeAccessToken);
        OrangeFreeSpace orangeFreeSpace = orangeCloudFoldersAPI.getAvailableSpace(orangeAccessToken);
        System.out.println(orangeFreeSpace);
        assertNotNull(orangeFreeSpace);
        assertTrue(orangeFreeSpace.getAvailableSpace() > 0L);

    }


}
