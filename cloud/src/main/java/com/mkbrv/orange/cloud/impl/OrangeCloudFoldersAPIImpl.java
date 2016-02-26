package com.mkbrv.orange.cloud.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mkbrv.orange.client.ExceptionAwareHttpClient;
import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.SimpleHttpClient;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.OrangeCloudFoldersAPI;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import com.mkbrv.orange.cloud.model.OrangeFreeSpaceDeserializer;

/**
 * Created by mikibrv on 20/02/16.
 */
public class OrangeCloudFoldersAPIImpl implements OrangeCloudFoldersAPI {

    private final OrangeContext orangeContext;

    private final OrangeHttpClient orangeHttpClient;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(OrangeFreeSpace.class,
            new OrangeFreeSpaceDeserializer()).create();

    public OrangeCloudFoldersAPIImpl(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = new ExceptionAwareHttpClient(new SimpleHttpClient());
    }

    public OrangeCloudFoldersAPIImpl(final OrangeContext orangeContext, final OrangeHttpClient orangeHttpClient) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = orangeHttpClient;
    }

    @Override
    public OrangeFreeSpace getAvailableSpace(final OrangeAccessToken orangeAccessToken) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getFreeSpace())
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFreeSpace.class);
    }

    @Override
    public OrangeFolder getRootFolder(final OrangeAccessToken orangeAccessToken) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(this.orangeContext.getOrangeURLs().getRootFolder())
                .setOrangeAccessToken(orangeAccessToken);
        OrangeResponse orangeResponse = this.orangeHttpClient.doGet(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeFolder.class);
    }

    @Override
    public OrangeFolder getFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder) {
        return null;
    }

    @Override
    public OrangeFolder createFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder) {
        return null;
    }

    @Override
    public OrangeFolder updateFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder) {
        return null;
    }

    @Override
    public void deleteFolder(OrangeAccessToken orangeAccessToken, OrangeFolder orangeFolder) {

    }
}
