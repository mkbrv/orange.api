package com.mkbrv.orange.identity;

import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;

/**
 * Created by mikibrv on 18/02/16.
 */
public interface OrangeIdentityAPI {

    /**
     * Builds the auth URL based on the orange context contained in the request;
     *
     * @param orangeContext uses the context inside;
     * @return URL
     */
    String buildOauthAuthorizeURL(OrangeIdentityContext orangeContext);

    /**
     * Will generate an access token based on the initial token provided by the OAUTH redirect
     * Also contains a refresh token
     *
     * @return accessToken with a refresh token;
     */
    OrangeAccessToken generateAccessAndRefreshTokenFromInitial(final OrangeContext orangeContext,
                                                               final OrangeAccessToken initialToken);

    void generateAccessTokenFromRefreshToken();

    /**
     * Constants related to the API call
     */
    class Constants {
        public static final String PARAM_RESPONSE_TYPE = "response_type";
        public static final String PARAM_CLIENT_ID = "client_id";
        public static final String PARAM_REDIRECT_URI = "redirect_uri";
        public static final String PARAM_SCOPE = "scope";
        public static final String PARAM_STATE = "state";
        public static final String PARAM_PROMPT = "prompt";
    }
}
