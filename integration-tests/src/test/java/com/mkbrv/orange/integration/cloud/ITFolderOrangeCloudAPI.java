package com.mkbrv.orange.integration.cloud;

import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.file.OrangeFileMetadata;
import com.mkbrv.orange.cloud.model.file.OrangeFileType;
import com.mkbrv.orange.cloud.model.folder.DefaultOrangeFolder;
import com.mkbrv.orange.cloud.model.freespace.OrangeFreeSpace;
import com.mkbrv.orange.cloud.request.OptionalFolderParams;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.gen5.api.Assertions.assertTrue;

/**
 * Integration Tests for Orange Cloud API
 * Created by mkbrv on 08/03/16.
 */
public class ITFolderOrangeCloudAPI extends ITRootFolderCloudAPI {

    private static final Logger LOG = LoggerFactory.getLogger(ITFolderOrangeCloudAPI.class);

    @BeforeEach @org.junit.Before
    public void init() throws IOException {
        super.init();
    }


    @Test @org.junit.Test
    public void getOneSpecificFolder() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        //for this we kind of have to get the root folder
        OrangeFolder rootFolder = orangeCloudFoldersAPI.getRootFolder(orangeAccessToken,
                new OptionalFolderParams().setShowThumbnails(""));

        //get the first folder from the root
        OrangeFolder orangeFolder = rootFolder.getSubFolders().get(0);
        orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken, orangeFolder, new OptionalFolderParams());
        assertNotNull(orangeFolder);
    }

    @Test @org.junit.Test
    public void invalidFolderIdThrowsException() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        //get the first folder from the root
        OrangeFolder orangeFolder = new DefaultOrangeFolder("randomId");
        orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken, orangeFolder, new OptionalFolderParams());
        assertNull(orangeFolder);
    }


    @Test @org.junit.Test
    public void flatRootFolderReturnsAllFiles() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder regularFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams());

        OrangeFolder flatFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                regularFolder,
                new OptionalFolderParams()
                        .setFlat(Boolean.TRUE));
        assertNotNull(flatFolder);


        //we expect to get all pictures and folders in one call.
        assertTrue(flatFolder.getFiles().size() > regularFolder.getFiles().size());
        assertTrue(flatFolder.getSubFolders().size() > regularFolder.getSubFolders().size());
    }

    @Test @org.junit.Test
    public void restrictedModeReturnsAppFolderAsRoot() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder regularFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams());

        OrangeFolder restrictedFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams()
                        .setRestrictedMode(""));

        assertNotNull(restrictedFolder);
        assertNotEquals(restrictedFolder.getId(), regularFolder.getId());
    }

    @Test @org.junit.Test
    public void showThumbnailsReturnsFileThumbnail() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams().setShowThumbnails(""));

        //do they all have it? Expect at least one to have them might be better
        final AtomicInteger countHasPreviewUrl = new AtomicInteger(0);
        orangeFolder.getFiles().forEach((file) -> {
            if (file.getPreviewUrl() != null && file.getThumbUrl() != null) {
                countHasPreviewUrl.incrementAndGet();
            }
        });

        if (orangeFolder.getFiles().size() > 0) {
            assertTrue(countHasPreviewUrl.get() > 0);
        }
    }

    /**
     * Account must have audio files. Flat is set to true
     */
    @Test @org.junit.Test
    public void filterReturnsOnlyAudioFiles() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams().setFlat(Boolean.TRUE).setFilter(OrangeFileType.AUDIO));

        orangeFolder.getFiles().forEach((file) -> {
            assertTrue(file.getType().equals(OrangeFileType.AUDIO));
            assertTrue(file.getMetadata().has(OrangeFileMetadata.Key.DURATION));
        });
    }

    /**
     * Account must have audio files. Flat is set to true
     */
    @Test @org.junit.Test
    public void filterReturnsOnlyVideoFiles() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams().setFlat(Boolean.TRUE).setFilter(OrangeFileType.VIDEO));

        orangeFolder.getFiles().forEach((file) -> {
            assertTrue(file.getType().equals(OrangeFileType.VIDEO));
            assertTrue(file.getMetadata().has(OrangeFileMetadata.Key.DURATION));
        });
    }


    @Test @org.junit.Test
    public void treeReturnsOnlySubFolders() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams().setTree(Boolean.TRUE));

        assertTrue(orangeFolder.getFiles().size() == 0);
    }

    @Test @org.junit.Test
    public void offsetSkipsExactNumberOfFolders() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        final Integer offset = 10;
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams().setFlat(Boolean.TRUE));

        OrangeFolder folderWithOffset = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                orangeFolder,
                new OptionalFolderParams().setFlat(Boolean.TRUE).setOffset(offset));
        this.assertsOffsetSkipsExactNumberOfFolders(orangeFolder, folderWithOffset, offset);
    }


    @Test @org.junit.Test
    public void limitsBringExactlyNumberOfFolders() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        final Integer limit = 10;

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams().setFlat(Boolean.TRUE).setLimit(limit));

        assertEquals(limit, Integer.valueOf(orangeFolder.getSubFolders().size()));
    }

    @Test @org.junit.Test
    public void limitAndOffsetBringsCorrect() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        final Integer limit = 10;
        final Integer offset = 10;

        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();
        OrangeFolder orangeFolder = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                this.getRootFolder().getSubFolders().get(0),
                new OptionalFolderParams().setFlat(Boolean.TRUE));

        OrangeFolder folderWithLimit = orangeCloudFoldersAPI.getFolder(orangeAccessToken,
                orangeFolder,
                new OptionalFolderParams().setFlat(Boolean.TRUE).setOffset(offset).setLimit(limit));

        this.assertLimitAndOffsetBringsCorrect(orangeFolder, folderWithLimit, offset, limit);
    }


    @Test @org.junit.Test
    public void getAvailableFreeSpace() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }
        OrangeAccessToken orangeAccessToken = this.getOrangeAccessToken();

        assertNotNull(orangeAccessToken);
        OrangeFreeSpace orangeFreeSpace = orangeCloudFoldersAPI.getAvailableSpace(orangeAccessToken);
        LOG.info("Orange Free space found : {} ", orangeFreeSpace);
        assertNotNull(orangeFreeSpace);
        assertTrue(orangeFreeSpace.getAvailableSpace() > 0L);
    }
}
