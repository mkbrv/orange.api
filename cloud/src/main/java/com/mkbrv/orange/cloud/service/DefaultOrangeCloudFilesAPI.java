package com.mkbrv.orange.cloud.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.request.UpdateRequestBody;
import com.mkbrv.orange.cloud.request.UploadFileRequest;
import com.mkbrv.orange.httpclient.ExceptionAwareHttpClient;
import com.mkbrv.orange.httpclient.OrangeContext;
import com.mkbrv.orange.httpclient.OrangeHttpClient;
import com.mkbrv.orange.httpclient.SimpleHttpClient;
import com.mkbrv.orange.httpclient.exception.OrangeException;
import com.mkbrv.orange.httpclient.request.OrangeRequest;
import com.mkbrv.orange.httpclient.request.OrangeUploadFileRequest;
import com.mkbrv.orange.httpclient.response.OrangeResponse;
import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFilesAPI;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.file.DefaultOrangeFile;
import com.mkbrv.orange.cloud.model.file.FileDeserializer;
import com.mkbrv.orange.cloud.model.folder.DefaultOrangeFolder;
import com.mkbrv.orange.cloud.response.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Orange Cloud Files API
 * Created by mkbrv on 09/03/16.
 */
public class DefaultOrangeCloudFilesAPI implements OrangeCloudFilesAPI {


    private static final Logger LOG = LoggerFactory.getLogger(DefaultOrangeCloudFilesAPI.class);

    /**
     *
     */
    private final OrangeContext orangeContext;

    /**
     *
     */
    private final OrangeHttpClient orangeHttpClient;


    /**
     * Default deserializers for Orange JSON to java classes
     */
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(OrangeFile.class, new FileDeserializer())
            .create();

    /**
     * Must have an orangeContext with api keys;
     * Has a default http httpclient;
     *
     * @param orangeContext contains api keys;
     */
    public DefaultOrangeCloudFilesAPI(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = new ExceptionAwareHttpClient(new SimpleHttpClient());
    }

    /**
     * A custom http httpclient can be added
     *
     * @param orangeContext    contains api keys;
     * @param orangeHttpClient custom http httpclient
     */
    public DefaultOrangeCloudFilesAPI(final OrangeContext orangeContext, final OrangeHttpClient orangeHttpClient) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = orangeHttpClient;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFile uploadFile(final OrangeAccessToken orangeAccessToken, final UploadFileRequest uploadFileRequest) {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("name", uploadFileRequest.getName());
        jsonBody.addProperty("size", uploadFileRequest.getFileSize());
        jsonBody.addProperty("folder", uploadFileRequest.getParentFolder().getId());

        OrangeRequest orangeRequest = new OrangeUploadFileRequest()
                .setFile(uploadFileRequest.getFile())
                .setUrl(orangeContext.getOrangeURLs().getUploadFile())
                .setOrangeAccessToken(orangeAccessToken)
                .setBody(jsonBody.toString());
        orangeRequest.addParameter("name", uploadFileRequest.getName());
        OrangeResponse orangeResponse = this.orangeHttpClient.uploadFile((OrangeUploadFileRequest) orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFile.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFile moveFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile,
                               final OrangeFolder folderWhereToMove) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFiles() + "/" + orangeFile.getId())
                .setOrangeAccessToken(orangeAccessToken);
        orangeRequest.setBody(new UpdateRequestBody().buildMoveRequestBody(folderWhereToMove));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFile.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFile copyFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile,
                               final OrangeFolder folderWhereToMove) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFiles() + "/" + orangeFile.getId())
                .setOrangeAccessToken(orangeAccessToken);
        orangeRequest.setBody(new UpdateRequestBody().buildCopyRequestBody(folderWhereToMove));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFile.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFile renameFile(final OrangeAccessToken orangeAccessToken, final OrangeFile orangeFile,
                                 final String newName) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFiles() + "/" + orangeFile.getId())
                .setOrangeAccessToken(orangeAccessToken);
        orangeRequest.setBody(new UpdateRequestBody().buildRenameRequestBody(newName));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFile.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFile getFile(final OrangeAccessToken orangeAccessToken, final String fileId) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFiles() + "/" + fileId)
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFile.class);

    }

    @Override
    public InputStream downloadFile(OrangeAccessToken orangeAccessToken, OrangeFile orangeFile) {
        if (orangeFile.getDownloadUrl() == null) {
            throw new OrangeException("Downlad url is not supposed to be empty", new NullPointerException());
        }
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(orangeFile.getDownloadUrl())
                .setOrangeAccessToken(orangeAccessToken);
        try {
            return this.orangeHttpClient.downloadFile(orangeRequest);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new OrangeException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericResponse deleteFile(OrangeAccessToken orangeAccessToken, OrangeFile orangeFile) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFiles() + "/" + orangeFile.getId())
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.delete(orangeRequest);
        return new GenericResponse(orangeResponse,
                OrangeCloudFoldersAPI.Constants.ORANGE_DELETE_OK_STATUS.equals(orangeResponse.getStatus()));
    }

}
