package com.mkbrv.orange.identity;

import com.mkbrv.orange.httpclient.OrangeHttpClient;
import com.mkbrv.orange.httpclient.response.OrangeResponse;
import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.httpclient.security.OrangeRefreshToken;
import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import com.mkbrv.orange.configuration.OrangeURLs;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;
import com.mkbrv.orange.identity.service.DefaultOrangeIdentityAPI;
import org.apache.commons.lang3.StringUtils;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Created by mkbrv on 17/02/16.
 */
public class DefaultOrangeIdentityAPITest {


    private OrangeIdentityAPI orangeIdentityAPI;

    private OrangeIdentityContext orangeContext;

    private OrangeClientConfiguration orangeClientConfiguration;

    private OrangeHttpClient orangeHttpClient;

    @BeforeEach @org.junit.Before
    public void init() {
        orangeHttpClient = Mockito.mock(OrangeHttpClient.class);

        orangeClientConfiguration = new OrangeClientConfiguration("appId", "clientId",
                "clientSecret", "http://AppRedirect.com");

        orangeContext = new OrangeIdentityContext(null);
        orangeContext.addScope(OrangeScope.cloudfullread).addScope(OrangeScope.offline_access);
        orangeContext.addPrompt(OrangePrompt.login).addPrompt(OrangePrompt.consent);
        orangeContext.setOrangeURLs(OrangeURLs.DEFAULT)
                .setOrangeClientConfiguration(orangeClientConfiguration);
        orangeIdentityAPI = new DefaultOrangeIdentityAPI(orangeContext, orangeHttpClient);
    }

    /**
     * Example of url:
     * orange.oauth.endpoint =https://api.orange.com/oauth/v2/authorize?scope=cloudfullread%20cloud%20openid&response_type=code&client_id={client_id}&prompt=login%20consent&state=state&redirect_uri={redirect_uri}
     */

    @Test @org.junit.Test
    public void canBuildAuthorizeURL() {

        String authorizeUrl = orangeIdentityAPI.buildOauthAuthorizeURL();
        assertNotNull(authorizeUrl);
        assertTrue(authorizeUrl.length() > 0);
        assertTrue(authorizeUrl.contains(orangeClientConfiguration.getClientId()));
        //the url is sanitized
        assertTrue(authorizeUrl.contains(orangeClientConfiguration.getAppRedirectURL().substring(10)));

        orangeContext.getOrangeScopeList()
                .forEach(orangeScope -> assertTrue(authorizeUrl.contains(orangeScope.toString())));


        //check all required params are there
        assertTrue(authorizeUrl.contains(DefaultOrangeIdentityAPI.Constants.PARAM_RESPONSE_TYPE));
        assertTrue(authorizeUrl.contains(DefaultOrangeIdentityAPI.Constants.PARAM_CLIENT_ID));
        assertTrue(authorizeUrl.contains(DefaultOrangeIdentityAPI.Constants.PARAM_REDIRECT_URI));
        assertTrue(authorizeUrl.contains(DefaultOrangeIdentityAPI.Constants.PARAM_SCOPE));
        assertTrue(authorizeUrl.contains(DefaultOrangeIdentityAPI.Constants.PARAM_STATE));
    }


    @Test @org.junit.Test
    public void canParseAccessTokenReply() {
        when(orangeHttpClient.doPost(any())).thenReturn(new OrangeResponse() {
            {
                setBody("{\n" +
                        "  \"token_type\": \"Bearer\",\n" +
                        "  \"access_token\": \"OFR-cbd58a8b0a75168414a97e73461951f2577186f53db5fe302f684488aa3dd0f04e702b441f4a11542e30a8d267dbd6f46134\",\n" +
                        "  \"expires_in\": 3600,\n" +
                        "  \"refresh_token\": \"OFR-80f6f9f745ce4b79f51bb86ee0d98a7ee5711c6c75229bf7d14d23f16f1ed3957f912da36d7aa8cccfeff88d2d6e1a3fcd76\"\n" +
                        "}\n");
                setStatus(200);
            }
        });

        OrangeAccessToken initialToken = new OrangeAccessToken("whatever");
        OrangeAccessToken orangeAccessToken = orangeIdentityAPI
                .generateAccessAndRefreshTokenFromInitial(initialToken);

        assertNotNull(orangeAccessToken);
        assertTrue(StringUtils.isNotEmpty(orangeAccessToken.getToken()), "Missing access token");
        assertTrue(StringUtils.isNotEmpty(orangeAccessToken.getRefreshToken().getToken()),
                "Refresh token was not recovered");
        assertTrue(orangeAccessToken.getExpirationTime().after(new Date()));
    }

    @Test @org.junit.Test
    public void canParseRefreshedToken() {
        when(orangeHttpClient.doPost(any())).thenReturn(new OrangeResponse() {
            {
                setBody("{\n" +
                        "  \"token_type\": \"Bearer\",\n" +
                        "  \"access_token\": \"OFR-1eab8052dda2c5e3f761987a33c84579d308afa20f5e9e704ce2be12a0674c7fa726c53aa24fe13c4abe3e349737c8e392e3\",\n" +
                        "  \"expires_in\": 3600\n" +
                        "}");
                setStatus(200);
            }
        });

        OrangeRefreshToken refreshToken = new OrangeRefreshToken("whatever");
        OrangeAccessToken orangeAccessToken = orangeIdentityAPI
                .generateAccessTokenFromRefreshToken(refreshToken);

        assertNotNull(orangeAccessToken);
        assertTrue(StringUtils.isNotEmpty(orangeAccessToken.getToken()), "Missing access token");
        assertEquals(refreshToken.getToken(), orangeAccessToken.getRefreshToken().getToken(),
                "Refresh token is not the same as used BeforeEach");
        assertTrue(orangeAccessToken.getExpirationTime().after(new Date()));
    }


}
