package com.mkbrv.orange.cloud;

import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.folder.DefaultOrangeFolder;
import com.mkbrv.orange.cloud.response.GenericResponse;

import java.io.File;

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
     * Update a file.
     *
     * @param orangeAccessToken
     * @param orangeFile
     * @return
     */
    OrangeFile updateFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile);

    /**
     * @param orangeAccessToken
     * @param orangeFile
     * @return
     */
    OrangeFile getFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile);

    /**
     * @param orangeAccessToken
     * @param orangeFile
     * @return
     */
    GenericResponse deleteFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile);

}
