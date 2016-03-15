package com.mkbrv.orange.cloud.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mkbrv.orange.client.ExceptionAwareHttpClient;
import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.SimpleHttpClient;
import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.file.OrangeFileDeserializer;
import com.mkbrv.orange.cloud.model.folder.OrangeFolderDeserializer;
import com.mkbrv.orange.cloud.model.freespace.OrangeFreeSpace;
import com.mkbrv.orange.cloud.model.freespace.OrangeFreeSpaceDeserializer;
import com.mkbrv.orange.cloud.request.OrangeFolderFilterParams;
import com.mkbrv.orange.cloud.request.OrangeFolderRequestParams;
import com.mkbrv.orange.cloud.response.OrangeGenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Map;


/**
 * {@inheritDoc}
 * Implementation of the OrangeCloudFoldersAPI using GSON
 * Created by mkbrv on 20/02/16.
 */
public class DefaultOrangeCloudFoldersAPI implements OrangeCloudFoldersAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultOrangeCloudFoldersAPI.class);

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
    protected Gson gson = new GsonBuilder()
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
    public DefaultOrangeCloudFoldersAPI(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = new ExceptionAwareHttpClient(new SimpleHttpClient());
    }

    /**
     * A custom http client can be added
     *
     * @param orangeContext    contains api keys;
     * @param orangeHttpClient custom http client
     */
    public DefaultOrangeCloudFoldersAPI(final OrangeContext orangeContext, final OrangeHttpClient orangeHttpClient) {
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
                                      final OrangeFolderFilterParams orangeFolderFilterParams) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders())
                .setOrangeAccessToken(orangeAccessToken);
        this.buildParametersListForRootFolder(orangeRequest, orangeFolderFilterParams);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    /**
     * @param orangeRequest            orange request where parameters are added
     * @param orangeFolderFilterParams orange optional parameters;
     */
    protected void buildParametersListForRootFolder(final OrangeRequest orangeRequest, final OrangeFolderFilterParams
            orangeFolderFilterParams) {
        if (orangeFolderFilterParams == null) {
            return;
        }
        this.addParameterIfNotNull(orangeRequest, Constants.RESTRICTED_MODE,
                orangeFolderFilterParams.getRestrictedMode());
        this.addParameterIfNotNull(orangeRequest, Constants.SHOW_THUMBNAILS,
                orangeFolderFilterParams.getShowThumbnails());
        this.addParameterIfNotNull(orangeRequest, Constants.FILTER,
                orangeFolderFilterParams.getFilter());
        this.addParameterIfNotNull(orangeRequest, Constants.FLAT,
                orangeFolderFilterParams.getFlat());
        this.addParameterIfNotNull(orangeRequest, Constants.TREE,
                orangeFolderFilterParams.getTree());
        this.addParameterIfNotNull(orangeRequest, Constants.LIMIT,
                orangeFolderFilterParams.getLimit());
        this.addParameterIfNotNull(orangeRequest, Constants.OFFSET,
                orangeFolderFilterParams.getOffset());
    }

    protected void addParameterIfNotNull(final OrangeRequest orangeRequest, final String parameterName,
                                         final Object parameterValue) {
        if (parameterValue != null) {
            orangeRequest.addParameter(parameterName, parameterValue.toString().toLowerCase().trim());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder getFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder,
                                  final OrangeFolderFilterParams orangeFolderFilterParams) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        try {
            OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
            this.buildParametersListForRootFolder(orangeRequest, orangeFolderFilterParams);
            return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
        } catch (OrangeException orangeException) {
            LOG.warn(orangeException.toString(), orangeException);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder createFolder(final OrangeAccessToken orangeAccessToken,
                                     final OrangeFolderRequestParams orangeFolder) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders())
                .setOrangeAccessToken(orangeAccessToken);
        assert (orangeFolder.getParentFolderId() != null);
        assert (orangeFolder.getName() != null);
        orangeRequest.setBody(this.buildJsonForUpdate(orangeFolder));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder updateFolder(final OrangeAccessToken orangeAccessToken,
                                     final OrangeFolder orangeFolder,
                                     final OrangeFolderRequestParams orangeFolderRequestParams) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        orangeRequest.setBody(this.buildJsonForUpdate(orangeFolderRequestParams));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    /**
     * @param orangeFolderRequestParams
     * @return body json
     */
    protected String buildJsonForUpdate(final OrangeFolderRequestParams orangeFolderRequestParams) {
        return gson.toJson(orangeFolderRequestParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeGenericResponse deleteFolder(OrangeAccessToken orangeAccessToken, OrangeFolder orangeFolder) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.delete(orangeRequest);
        return new OrangeGenericResponse(orangeResponse,
                Constants.ORANGE_DELETE_OK_STATUS.equals(orangeResponse.getStatus()));
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
