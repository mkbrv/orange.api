package com.mkbrv.orange.cloud.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mkbrv.orange.httpclient.ExceptionAwareHttpClient;
import com.mkbrv.orange.httpclient.OrangeContext;
import com.mkbrv.orange.httpclient.OrangeHttpClient;
import com.mkbrv.orange.httpclient.SimpleHttpClient;
import com.mkbrv.orange.httpclient.request.OrangeRequest;
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
    public OrangeFile uploadFile(OrangeAccessToken orangeAccessToken, DefaultOrangeFolder orangeFolder, File file) {
        return new DefaultOrangeFile();
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
    public GenericResponse deleteFile(OrangeAccessToken orangeAccessToken, OrangeFile orangeFile) {
        return null;
    }
}
