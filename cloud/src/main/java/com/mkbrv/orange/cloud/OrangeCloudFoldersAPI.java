package com.mkbrv.orange.cloud;

import com.google.gson.JsonDeserializer;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import com.mkbrv.orange.cloud.request.OrangeFolderRequestParams;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by mikibrv on 20/02/16.
 */
public interface OrangeCloudFoldersAPI {

    /**
     * Returns free space of an account;
     *
     * @param orangeAccessToken
     * @return
     */
    OrangeFreeSpace getAvailableSpace(final OrangeAccessToken orangeAccessToken);


    /**
     * @param orangeAccessToken
     * @return
     */
    OrangeFolder getRootFolder(final OrangeAccessToken orangeAccessToken,
                               final OrangeFolderRequestParams orangeFolderRequestParams);

    /**
     * @param orangeAccessToken
     * @param orangeFolder
     * @return
     */
    OrangeFolder getFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);

    /**
     * @param orangeAccessToken
     * @param orangeFolder
     * @return
     */
    OrangeFolder createFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);

    /**
     * @param orangeAccessToken
     * @param orangeFolder
     * @return
     */
    OrangeFolder updateFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);


    /**
     * @param orangeAccessToken
     * @param orangeFolder
     */
    void deleteFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);


    /**
     * Custom serializers for the context. Defaults ones are provided. This will override the default ones;
     *
     * @param gsonTypeAdapters
     */
    void setGsonTypeAdapters(Map<Type, JsonDeserializer> gsonTypeAdapters);


}
