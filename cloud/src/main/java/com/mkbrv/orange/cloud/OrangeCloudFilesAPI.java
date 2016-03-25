package com.mkbrv.orange.cloud;

import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.folder.DefaultOrangeFolder;
import com.mkbrv.orange.cloud.response.GenericResponse;

import java.io.File;
import java.io.InputStream;

/**
 * Created by mkbrv on 20/02/16.
 */
public interface OrangeCloudFilesAPI {

    /**
     * Uploads a file to a folder
     *
     * @param orangeFolder where to upload the file
     * @param file         file to be uploaded
     * @return
     */
    OrangeFile uploadFile(final OrangeAccessToken orangeAccessToken,
                          final DefaultOrangeFolder orangeFolder, final File file);


    /**
     * @param orangeAccessToken
     * @param orangeFile
     * @param folderWhereToMove
     * @return
     */
    OrangeFile moveFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile,
                        final OrangeFolder folderWhereToMove);

    /**
     * @param orangeAccessToken
     * @param orangeFile
     * @param folderWhereToMove
     * @return
     */
    OrangeFile copyFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile,
                        final OrangeFolder folderWhereToMove);


    /**
     * @param orangeAccessToken
     * @param orangeFile
     * @param newName
     * @return
     */
    OrangeFile renameFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile,
                          final String newName);

    /**
     * @param orangeAccessToken
     * @return
     */
    OrangeFile getFile(final OrangeAccessToken orangeAccessToken, final String fileId);


    /**
     * @param orangeAccessToken
     * @param orangeFile
     * @return
     */
    InputStream downloadFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile);

    /**
     * @param orangeAccessToken
     * @param orangeFile
     * @return
     */
    GenericResponse deleteFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile);

}
