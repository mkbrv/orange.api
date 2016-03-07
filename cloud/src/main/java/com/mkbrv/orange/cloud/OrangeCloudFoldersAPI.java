package com.mkbrv.orange.cloud;

import com.google.gson.JsonDeserializer;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFolder;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;
import com.mkbrv.orange.cloud.request.OrangeFolderRequestParams;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * As described in: https://developer.orange.com/apis/cloud-france/api-reference
 * <p>
 * Created by mkbrv on 20/02/16.
 */
public interface OrangeCloudFoldersAPI {

    /**
     * Returns free space of an account;
     * Orange Cloud API: GET /freespace
     *
     * @param orangeAccessToken Users Access Token used to validate his session;
     * @return OrangeFreeSpace available free space;
     */
    OrangeFreeSpace getAvailableSpace(final OrangeAccessToken orangeAccessToken);


    /**
     * Orange Cloud API: GET /folders
     *
     * @param orangeAccessToken         Users Access Token used to validate his session;
     * @param orangeFolderRequestParams Parameters which specify how the data should be returned;
     * @return OrangeFolder root folder of the user with specifications described in the orangeFolderRequestParams
     */
    OrangeFolder getRootFolder(final OrangeAccessToken orangeAccessToken,
                               final OrangeFolderRequestParams orangeFolderRequestParams);

    /**
     * @param orangeAccessToken Users Access Token used to validate his session;
     * @param orangeFolder      Contains the id of the folder which is required
     * @return OrangeFolder returned folder
     */
    OrangeFolder getFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);

    /**
     * @param orangeAccessToken Users Access Token used to validate his session;
     * @param orangeFolder      Contains the name of the created folder and his parent id
     * @return OrangeFolder created folder
     */
    OrangeFolder createFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);

    /**
     * @param orangeAccessToken Users Access Token used to validate his session;
     * @param orangeFolder      Folder to update
     * @return OrangeFolder Updated Folder
     */
    OrangeFolder updateFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);


    /**
     * @param orangeAccessToken Users Access Token used to validate his session;
     * @param orangeFolder      Folder to be removed
     */
    void deleteFolder(final OrangeAccessToken orangeAccessToken, final OrangeFolder orangeFolder);


    /**
     * Custom deserialization for the context. Defaults ones are provided. This will override the default ones;
     * Allows adding custom json deserializers with gson;
     *
     * @param gsonTypeAdapters Optional Custom deserializers for the JSON returned from Orange
     */
    void setGsonTypeAdapters(final Map<Type, JsonDeserializer> gsonTypeAdapters);

    class Constants {

        public static final String RESTRICTED_MODE = "restrictedmode";
        public static final String SHOW_THUMBNAILS = "showthumbnails";
        public static final String FILTER = "filter";
        public static final String FLAT = "flat";
        public static final String TREE = "tree";
        public static final String LIMIT = "limit";
        public static final String OFFSET = "offset";


        public static final String HEADER_AUTHORIZATION = "Authorization";
        public static final String HEADER_AUTHORIZATION_BASIC = "Basic";
    }


}
