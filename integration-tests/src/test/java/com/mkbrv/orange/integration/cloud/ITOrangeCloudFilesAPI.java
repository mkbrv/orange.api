package com.mkbrv.orange.integration.cloud;

import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFilesAPI;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.request.OrangeFolderFilterParams;
import com.mkbrv.orange.cloud.service.OrangeCloudFilesAPIImpl;
import com.mkbrv.orange.cloud.service.OrangeCloudFoldersAPIImpl;
import com.mkbrv.orange.integration.identity.AbstractIdentityIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by mkbrv on 11/03/16.
 */
public class ITOrangeCloudFilesAPI extends AbstractIdentityIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ITOrangeCloudFilesAPI.class);
    OrangeCloudFilesAPI orangeCloudFilesAPI;
    OrangeCloudFoldersAPI orangeCloudFoldersAPI;

    @Before
    public void init() throws IOException {
        super.init();
        orangeCloudFilesAPI = new OrangeCloudFilesAPIImpl(this.orangeContext);
        orangeCloudFoldersAPI = new OrangeCloudFoldersAPIImpl(this.orangeContext);
    }

    @Test
    public void retrieveFileFromRootFolder() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OrangeFolderFilterParams());

        OrangeFile orangeFile = rootFolder.getFiles().get(0);
        orangeFile = orangeCloudFilesAPI.getFile(orangeAccessToken, orangeFile);
        assertNotNull(orangeFile);
        assertNotNull(orangeFile.getDownloadUrl());
    }


}
