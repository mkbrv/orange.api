package com.mkbrv.orange.cloud.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mkbrv.orange.client.ExceptionAwareHttpClient;
import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.SimpleHttpClient;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.*;
import com.mkbrv.orange.cloud.request.OrangeFolderRequestParams;

import java.lang.reflect.Type;
import java.util.Map;


/**
 * {@inheritDoc}
 * Implementation of the OrangeCloudFoldersAPI using GSON
 * Created by mkbrv on 20/02/16.
 */
public class OrangeCloudFoldersAPIImpl implements OrangeCloudFoldersAPI {

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
            .registerTypeAdapter(OrangeFreeSpace.class, new OrangeFreeSpaceDeserializer())
            .registerTypeAdapter(OrangeFolder.class, new OrangeFolderDeserializer())
            .registerTypeAdapter(OrangeFile.class, new OrangeFileDeserializer())
            .create();

    /**
     * Must have an orangeContext with api keys;
     * Has a default http client;
     *
     * @param orangeContext contains api keys;
     */
    public OrangeCloudFoldersAPIImpl(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = new ExceptionAwareHttpClient(new SimpleHttpClient());
    }

    /**
     * A custom http client can be added
     *
     * @param orangeContext    contains api keys;
     * @param orangeHttpClient custom http client
     */
    public OrangeCloudFoldersAPIImpl(final OrangeContext orangeContext, final OrangeHttpClient orangeHttpClient) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = orangeHttpClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFreeSpace getAvailableSpace(final OrangeAccessToken orangeAccessToken) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFreeSpace())
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFreeSpace.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder getRootFolder(final OrangeAccessToken orangeAccessToken,
                                      final OrangeFolderRequestParams orangeFolderRequestParams) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getRootFolder())
                .setOrangeAccessToken(orangeAccessToken);
        this.buildParametersListForRootFolder(orangeRequest, orangeFolderRequestParams);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    /**
     * @param orangeRequest             orange request where parameters are added
     * @param orangeFolderRequestParams orange optional parameters;
     */
    private void buildParametersListForRootFolder(final OrangeRequest orangeRequest, final OrangeFolderRequestParams
            orangeFolderRequestParams) {
        if (orangeFolderRequestParams == null) {
            return;
        }
        this.addParameterIfNotNull(orangeRequest, Constants.RESTRICTED_MODE,
                orangeFolderRequestParams.getRestrictedMode());
        this.addParameterIfNotNull(orangeRequest, Constants.SHOW_THUMBNAILS,
                orangeFolderRequestParams.getShowThumbnails());
        this.addParameterIfNotNull(orangeRequest, Constants.FILTER,
                orangeFolderRequestParams.getFilter());
        this.addParameterIfNotNull(orangeRequest, Constants.FLAT,
                orangeFolderRequestParams.getFlat());
        this.addParameterIfNotNull(orangeRequest, Constants.TREE,
                orangeFolderRequestParams.getTree());
        this.addParameterIfNotNull(orangeRequest, Constants.LIMIT,
                orangeFolderRequestParams.getLimit());
        this.addParameterIfNotNull(orangeRequest, Constants.OFFSET,
                orangeFolderRequestParams.getOffset());
    }

    private void addParameterIfNotNull(final OrangeRequest orangeRequest, final String parameterName,
                                       final Object parameterValue) {
        if (parameterValue != null) {
            orangeRequest.addParameter(parameterName, parameterValue.toString().toLowerCase().trim());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder getFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder createFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder updateFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFolder(OrangeAccessToken orangeAccessToken, OrangeFolder orangeFolder) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGsonTypeAdapters(final Map<Type, JsonDeserializer> gsonTypeAdapters) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonTypeAdapters.forEach(gsonBuilder::registerTypeAdapter);
        this.gson = gsonBuilder.create();
    }
}
