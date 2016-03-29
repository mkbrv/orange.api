package com.mkbrv.orange.integration.cloud;

import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.file.OrangeFileMetadata;
import com.mkbrv.orange.cloud.model.file.OrangeFileType;
import com.mkbrv.orange.cloud.request.OptionalFolderParams;
import com.mkbrv.orange.cloud.service.DefaultOrangeCloudFoldersAPI;
import com.mkbrv.orange.integration.identity.AbstractIdentityIntegrationTest;
import org.junit.Before;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;


/**
 * Created by mkbrv on 20/02/16.
 */
public class ITRootFolderCloudAPI extends AbstractIdentityIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ITRootFolderCloudAPI.class);
    OrangeCloudFoldersAPI orangeCloudFoldersAPI;

    @BeforeAll
    @Before
    public void init() throws IOException {
        super.init();
        orangeCloudFoldersAPI = new DefaultOrangeCloudFoldersAPI(this.orangeContext);
    }


    protected OrangeFolder getRootFolder() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return null;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        assertNotNull(orangeAccessToken);
        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken, null);
        assertNotNull(orangeFolder);
        return orangeFolder;
    }

    @Test
    @org.junit.Test
    public void rootFolderCanBeFetched() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeFolder orangeFolder = this.getRootFolder();
        assertNotNull(orangeFolder);
    }


    @Test
    @org.junit.Test
    public void flatRootFolderReturnsAllFiles() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder regularRootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams());

        OrangeFolder flatRootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams()
                        .setFlat(Boolean.TRUE));
        assertNotNull(flatRootFolder);


        //we expect to get all pictures and folders in one call.
        assertTrue(flatRootFolder.getFiles().size() > regularRootFolder.getFiles().size());
        assertTrue(flatRootFolder.getSubFolders().size() > regularRootFolder.getSubFolders().size());
    }

    @Test
    @org.junit.Test
    public void restrictedModeReturnsAppFolderAsRoot() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder regularRootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams());

        OrangeFolder restrictedFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams()
                        .setRestrictedMode(""));

        assertNotNull(restrictedFolder);
        assertNotEquals(restrictedFolder.getId(), regularRootFolder.getId());
    }

    @Test
    @org.junit.Test
    public void showThumbnailsReturnsFileThumbnail() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setShowThumbnails(""));

        //do they all have it? Expect at least one to have them might be better
        final AtomicInteger countHasPreviewUrl = new AtomicInteger(0);
        rootFolder.getFiles().forEach((file) -> {
            if (file.getPreviewUrl() != null && file.getThumbUrl() != null) {
                countHasPreviewUrl.incrementAndGet();
            }
        });

        if (rootFolder.getFiles().size() > 0) {
            assertTrue(countHasPreviewUrl.get() > 0);
        }
    }

    /**
     * Account must have audio files. Flat is set to true
     */
    @Test
    @org.junit.Test
    public void filterReturnsOnlyAudioFiles() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setFlat(Boolean.TRUE).setFilter(OrangeFileType.AUDIO));

        rootFolder.getFiles().forEach((file) -> {
            assertTrue(file.getType().equals(OrangeFileType.AUDIO));
            assertTrue(file.getMetadata().has(OrangeFileMetadata.Key.DURATION));
        });
    }

    /**
     * Account must have audio files. Flat is set to true
     */
    @Test
    @org.junit.Test
    public void filterReturnsOnlyVideoFiles() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setFlat(Boolean.TRUE).setFilter(OrangeFileType.VIDEO));

        rootFolder.getFiles().forEach((file) -> {
            assertTrue(file.getType().equals(OrangeFileType.VIDEO));
            assertTrue(file.getMetadata().has(OrangeFileMetadata.Key.DURATION));
        });
    }


    @Test
    @org.junit.Test
    public void treeReturnsOnlySubFolders() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setTree(Boolean.TRUE));

        assertTrue(rootFolder.getFiles().size() == 0);
    }

    @Test
    @org.junit.Test
    public void offsetSkipsExactNumberOfFolders() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        final Integer offset = 10;
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setFlat(Boolean.TRUE));

        OrangeFolder folderWithOffset = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setFlat(Boolean.TRUE).setOffset(offset));
        this.assertsOffsetSkipsExactNumberOfFolders(rootFolder, folderWithOffset, offset);
    }

    /**
     * Assertions for the above method
     *
     * @param regularFolder
     * @param folderWithOffset
     * @param offset
     */
    protected void assertsOffsetSkipsExactNumberOfFolders(final OrangeFolder regularFolder,
                                                          final OrangeFolder folderWithOffset, final Integer offset) {
        //first 10 should be different, the next one should be included in the folderWithOffset
        for (int index = 0; index < offset; index++) {
            assertNotEquals(regularFolder.getSubFolders().get(index), folderWithOffset.getSubFolders().get(index));
        }

        assertEquals(regularFolder.getSubFolders().get(offset), folderWithOffset.getSubFolders().get(0));
    }

    @Test
    @org.junit.Test
    public void limitsBringExactlyNumberOfFolders() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        final Integer limit = 10;

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        OrangeFolder folderWithLimit = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setFlat(Boolean.TRUE).setLimit(limit));

        assertEquals(limit, Integer.valueOf(folderWithLimit.getSubFolders().size()));
    }

    @Test
    @org.junit.Test
    public void limitAndOffsetBringsCorrect() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        final Integer limit = 10;
        final Integer offset = 10;

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setFlat(Boolean.TRUE));

        OrangeFolder folderWithLimit = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setFlat(Boolean.TRUE).setOffset(offset).setLimit(limit));

        this.assertLimitAndOffsetBringsCorrect(rootFolder, folderWithLimit, offset, limit);
    }

    /**
     * Asserts for above method
     *
     * @param regularFolder
     * @param folderWithLimit
     * @param offset
     * @param limit
     */
    protected void assertLimitAndOffsetBringsCorrect(final OrangeFolder regularFolder, final OrangeFolder folderWithLimit,
                                                     final Integer offset, final Integer limit) {
        assertEquals(limit, Integer.valueOf(folderWithLimit.getSubFolders().size()));
        //first 10 should be different, the next one should be included in the folderWithOffset
        for (int index = 0; index < offset; index++) {
            assertNotEquals(regularFolder.getSubFolders().get(index), folderWithLimit.getSubFolders().get(index));
        }
    }


}
