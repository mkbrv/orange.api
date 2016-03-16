package com.mkbrv.orange.cloud.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mkbrv.orange.client.ExceptionAwareHttpClient;
import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.SimpleHttpClient;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFilesAPI;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.file.OrangeFileDeserializer;
import com.mkbrv.orange.cloud.model.folder.DefaultOrangeFolder;
import com.mkbrv.orange.cloud.response.OrangeGenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
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
            .registerTypeAdapter(OrangeFile.class, new OrangeFileDeserializer())
            .create();

    /**
     * Must have an orangeContext with api keys;
     * Has a default http client;
     *
     * @param orangeContext contains api keys;
     */
    public DefaultOrangeCloudFilesAPI(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = new ExceptionAwareHttpClient(new SimpleHttpClient());
    }

    /**
     * A custom http client can be added
     *
     * @param orangeContext    contains api keys;
     * @param orangeHttpClient custom http client
     */
    public DefaultOrangeCloudFilesAPI(final OrangeContext orangeContext, final OrangeHttpClient orangeHttpClient) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = orangeHttpClient;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeGenericResponse uploadFile(OrangeAccessToken orangeAccessToken, DefaultOrangeFolder orangeFolder, File file) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFile updateFile(OrangeAccessToken orangeAccessToken, OrangeFile orangeFile) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFile getFile(OrangeAccessToken orangeAccessToken, OrangeFile orangeFile) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFiles() + "/" + orangeFile.getId())
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFile.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeGenericResponse deleteFile(OrangeAccessToken orangeAccessToken, OrangeFile orangeFile) {
        return null;
    }
}
