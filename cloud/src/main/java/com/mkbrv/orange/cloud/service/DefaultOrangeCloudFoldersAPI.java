package com.mkbrv.orange.cloud.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mkbrv.orange.cloud.request.UpdateFolderRequest;
import com.mkbrv.orange.httpclient.ExceptionAwareHttpClient;
import com.mkbrv.orange.httpclient.OrangeContext;
import com.mkbrv.orange.httpclient.OrangeHttpClient;
import com.mkbrv.orange.httpclient.SimpleHttpClient;
import com.mkbrv.orange.httpclient.exception.OrangeException;
import com.mkbrv.orange.httpclient.request.OrangeRequest;
import com.mkbrv.orange.httpclient.response.OrangeResponse;
import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.OrangeFile;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.file.FileDeserializer;
import com.mkbrv.orange.cloud.model.folder.FolderDeserializer;
import com.mkbrv.orange.cloud.model.folder.FolderSerializer;
import com.mkbrv.orange.cloud.model.freespace.OrangeFreeSpace;
import com.mkbrv.orange.cloud.model.freespace.FreeSpaceDeserializer;
import com.mkbrv.orange.cloud.request.OptionalFolderParams;
import com.mkbrv.orange.cloud.response.GenericResponse;
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
    protected final OrangeContext orangeContext;

    /**
     *
     */
    protected final OrangeHttpClient orangeHttpClient;

    /**
     * Default deserializers for Orange JSON to java classes
     */
    protected Gson gson = new GsonBuilder()
            .registerTypeAdapter(OrangeFreeSpace.class, new FreeSpaceDeserializer())
            .registerTypeAdapter(OrangeFolder.class, new FolderDeserializer())
            .registerTypeAdapter(OrangeFile.class, new FileDeserializer())
            .registerTypeAdapter(OrangeFolder.class, new FolderSerializer())
            .create();

    /**
     * Must have an orangeContext with api keys;
     * Has a default http httpclient;
     *
     * @param orangeContext contains api keys;
     */
    public DefaultOrangeCloudFoldersAPI(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = new ExceptionAwareHttpClient(new SimpleHttpClient());
    }

    /**
     * A custom http httpclient can be added
     *
     * @param orangeContext    contains api keys;
     * @param orangeHttpClient custom http httpclient
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
                                      final OptionalFolderParams optionalFolderParams) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders())
                .setOrangeAccessToken(orangeAccessToken);
        this.buildParametersListForRootFolder(orangeRequest, optionalFolderParams);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    /**
     * @param orangeRequest        orange request where parameters are added
     * @param optionalFolderParams orange optional parameters;
     */
    protected void buildParametersListForRootFolder(final OrangeRequest orangeRequest, final OptionalFolderParams
            optionalFolderParams) {
        if (optionalFolderParams == null) {
            return;
        }
        this.addParameterIfNotNull(orangeRequest, Constants.RESTRICTED_MODE,
                optionalFolderParams.getRestrictedMode());
        this.addParameterIfNotNull(orangeRequest, Constants.SHOW_THUMBNAILS,
                optionalFolderParams.getShowThumbnails());
        this.addParameterIfNotNull(orangeRequest, Constants.FILTER,
                optionalFolderParams.getFilter());
        this.addParameterIfNotNull(orangeRequest, Constants.FLAT,
                optionalFolderParams.getFlat());
        this.addParameterIfNotNull(orangeRequest, Constants.TREE,
                optionalFolderParams.getTree());
        this.addParameterIfNotNull(orangeRequest, Constants.LIMIT,
                optionalFolderParams.getLimit());
        this.addParameterIfNotNull(orangeRequest, Constants.OFFSET,
                optionalFolderParams.getOffset());
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
    public OrangeFolder createFolder(final OrangeAccessToken orangeAccessToken,
                                     final OrangeFolder orangeFolder) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders())
                .setOrangeAccessToken(orangeAccessToken);
        assert (orangeFolder.getParentFolderId() != null);
        assert (orangeFolder.getName() != null);
        orangeRequest.setBody(gson.toJson(orangeFolder));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeFolder getFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder,
                                  final OptionalFolderParams optionalFolderParams) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        try {
            OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
            this.buildParametersListForRootFolder(orangeRequest, optionalFolderParams);
            return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
        } catch (OrangeException orangeException) {
            LOG.warn(orangeException.toString(), orangeException);
            return null;
        }
    }

    @Override
    public OrangeFolder renameFolder(OrangeAccessToken orangeAccessToken,
                                     OrangeFolder orangeFolder, String newName) {

        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        orangeRequest.setBody(new UpdateFolderRequest().buildRenameFolderRequest(newName));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    @Override
    public OrangeFolder copyFolder(OrangeAccessToken orangeAccessToken,
                                   OrangeFolder orangeFolder, OrangeFolder newParentFolder) {

        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        orangeRequest.setBody(new UpdateFolderRequest().buildCopyFolderRequest(newParentFolder));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    @Override
    public OrangeFolder moveFolder(OrangeAccessToken orangeAccessToken,
                                   OrangeFolder orangeFolder, OrangeFolder newParentFolder) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        orangeRequest.setBody(new UpdateFolderRequest().buildMoveFolderRequest(newParentFolder));
        OrangeResponse orangeResponse = this.orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericResponse deleteFolder(OrangeAccessToken orangeAccessToken, OrangeFolder orangeFolder) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFolders() + "/" + orangeFolder.getId())
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.delete(orangeRequest);
        return new GenericResponse(orangeResponse,
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
