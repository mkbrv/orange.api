package com.mkbrv.orange.cloud;

import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.request.UploadFileRequest;
import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFile;
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
     * @param orangeAccessToken
     * @param uploadFileRequest file to be uploaded
     * @return file
     */
    OrangeFile uploadFile(final OrangeAccessToken orangeAccessToken,
                          final UploadFileRequest uploadFileRequest);


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
