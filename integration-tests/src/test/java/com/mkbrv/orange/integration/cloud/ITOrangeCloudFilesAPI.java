package com.mkbrv.orange.integration.cloud;

import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFilesAPI;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.request.OptionalFolderParams;
import com.mkbrv.orange.cloud.service.DefaultOrangeCloudFilesAPI;
import com.mkbrv.orange.cloud.service.DefaultOrangeCloudFoldersAPI;
import com.mkbrv.orange.integration.identity.AbstractIdentityIntegrationTest;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.gen5.api.Assertions.assertNotNull;


/**
 * Created by mkbrv on 11/03/16.
 */
public class ITOrangeCloudFilesAPI extends AbstractIdentityIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ITOrangeCloudFilesAPI.class);
    OrangeCloudFilesAPI orangeCloudFilesAPI;
    OrangeCloudFoldersAPI orangeCloudFoldersAPI;

    @BeforeAll
    public void init() throws IOException {
        super.init();
        orangeCloudFilesAPI = new DefaultOrangeCloudFilesAPI(this.orangeContext);
        orangeCloudFoldersAPI = new DefaultOrangeCloudFoldersAPI(this.orangeContext);
    }

    @Test
    @org.junit.Test
    public void retrieveFileFromRootFolder() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams());

        OrangeFile orangeFile = rootFolder.getFiles().get(0);
        orangeFile = orangeCloudFilesAPI.getFile(orangeAccessToken, orangeFile.getId());
        assertNotNull(orangeFile);
        assertNotNull(orangeFile.getDownloadUrl());
    }


}
