package com.mkbrv.orange.cloud;

import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.SimpleHttpClient;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.impl.OrangeCloudFoldersAPIImpl;
import com.mkbrv.orange.cloud.model.OrangeFileType;
import com.mkbrv.orange.cloud.request.OrangeFolderRequestParams;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by mkbrv on 03/03/16.
 */
public class OrangeRootFolderOptionalParametersTests extends AbstractOrangeCloudAPITests {

    private static final Logger LOG = LoggerFactory.getLogger(OrangeRootFolderOptionalParametersTests.class);

    @Before
    public void init() throws IOException {
        OrangeContext orangeContext = new OrangeContext().setOrangeClientConfiguration(this.orangeClientConfiguration);
        orangeHttpClient = Mockito.spy(new SimpleHttpClient());
        orangeCloudFoldersAPI = new OrangeCloudFoldersAPIImpl(orangeContext, orangeHttpClient);
    }

    @Test
    public void testOptionalParameterIsPresent() {
        OrangeFolderRequestParams requestParams = new OrangeFolderRequestParams()
                .setFilter(OrangeFileType.AUDIO)
                .setFlat(Boolean.TRUE)
                .setLimit(100)
                .setOffset(100)
                .setRestrictedMode("")
                .setShowThumbnails("")
                .setTree(Boolean.TRUE);

        doAnswer(invocationOnMock -> {
            //we have to check for each of the tested parameters to be present (and values)
            OrangeRequest orangeRequest = (OrangeRequest) invocationOnMock.getArguments()[0];
            assertNotNull(orangeRequest);
            assertEquals(7, orangeRequest.getParameters().size());//as above
            return new OrangeResponse().setBody(this.readValidResponseBody("rootfolder.json"))
                    .setStatus(200);
        }).when(this.orangeHttpClient).doGet(any());

        orangeCloudFoldersAPI.getRootFolder(new OrangeAccessToken("bla"), requestParams);
    }

}
