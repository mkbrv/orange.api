package com.mkbrv.orange.identity;

import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.client.security.OrangeRefreshToken;
import com.mkbrv.orange.identity.impl.OrangeIdentityAPIImpl;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;

/**
 * Created by mikibrv on 18/02/16.
 */
public interface OrangeIdentityAPI {

    /**
     * default implementation
     *
     * @param orangeContext orange context required
     * @return
     */
    static OrangeIdentityAPI newInstance(final OrangeIdentityContext orangeContext) {
        return new OrangeIdentityAPIImpl(orangeContext);
    }

    /**
     * Builds the auth URL based on the orange context contained in the request;
     * <p>
     * uses the context inside;
     *
     * @return URL
     */
    String buildOauthAuthorizeURL();

    /**
     * Will generate an access token based on the initial token provided by the OAUTH redirect
     * Also contains a refresh token
     *
     * @param initialToken initial token from Orange OAUTH
     * @return accessToken with a refresh token;
     */
    OrangeAccessToken generateAccessAndRefreshTokenFromInitial(
            final OrangeAccessToken initialToken);

    /**
     * Generates an access token based on a refresh token.
     *
     * @param orangeRefreshToken refresh token used to generate the access
     * @return OrangeAccessToken
     */
    OrangeAccessToken generateAccessTokenFromRefreshToken(final OrangeRefreshToken orangeRefreshToken);

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

        public static final String PARAM_GRANT_TYPE = "grant_type";
        public static final String PARAM_CODE = "code";
        public static final String PARAM_AUTH_CODE = "authorization_code";
        public static final String PARAM_REFRESH_TOKEN = "refresh_token";


        public static final String HEADER_AUTHORIZATION = "Authorization";
        public static final String HEADER_AUTHORIZATION_BASIC = "Basic";

    }
}
