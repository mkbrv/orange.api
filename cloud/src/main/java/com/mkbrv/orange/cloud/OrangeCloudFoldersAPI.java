package com.mkbrv.orange.cloud;

import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.cloud.model.OrangeFreeSpace;

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


}
