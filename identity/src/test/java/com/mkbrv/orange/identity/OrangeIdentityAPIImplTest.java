package com.mkbrv.orange.identity;

import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import com.mkbrv.orange.configuration.OrangeURLs;
import com.mkbrv.orange.identity.impl.OrangeIdentityAPIImpl;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mikibrv on 17/02/16.
 */
public class OrangeIdentityAPIImplTest {

    private OrangeIdentityAPI orangeIdentityAPI;

    private OrangeContext orangeContext;

    private OrangeClientConfiguration orangeClientConfiguration;

    private OrangeHttpClient orangeHttpClient;

    @Before
    public void init() {
        orangeHttpClient = Mockito.mock(OrangeHttpClient.class);
        orangeIdentityAPI = new OrangeIdentityAPIImpl(orangeHttpClient);

        orangeClientConfiguration = new OrangeClientConfiguration("appId", "clientId",
                "clientSecret", "http://AppRedirect.com");

        orangeContext = new OrangeContext().setOrangeURLs(OrangeURLs.DEFAULT)
                .setOrangeClientConfiguration(orangeClientConfiguration);
    }

    /**
     * Example of url:
     * orange.oauth.endpoint =https://api.orange.com/oauth/v2/authorize?scope=cloudfullread%20cloud%20openid&response_type=code&client_id={client_id}&prompt=login%20consent&state=state&redirect_uri={redirect_uri}
     */

    @Test
    public void canBuildAuthorizeURL() {
        OrangeIdentityContext orangeIdentityContext = new OrangeIdentityContext(orangeContext);
        orangeIdentityContext.addScope(OrangeScope.cloudfullread).addScope(OrangeScope.offline_access);
        orangeIdentityContext.addPrompt(OrangePrompt.login);

        String authorizeUrl = orangeIdentityAPI.buildOauthAuthorizeURL(orangeIdentityContext);
        assertNotNull(authorizeUrl);
        assertTrue(authorizeUrl.length() > 0);
        assertTrue(authorizeUrl.contains(orangeClientConfiguration.getClientId()));
        //the url is sanitized
        assertTrue(authorizeUrl.contains(orangeClientConfiguration.getAppRedirectURL().substring(10)));

        orangeIdentityContext.getOrangeScopeList()
                .forEach(orangeScope -> assertTrue(authorizeUrl.contains(orangeScope.toString())));


        //check all required params are there
        assertTrue(authorizeUrl.contains(OrangeIdentityAPIImpl.Constants.PARAM_RESPONSE_TYPE));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPIImpl.Constants.PARAM_CLIENT_ID));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPIImpl.Constants.PARAM_REDIRECT_URI));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPIImpl.Constants.PARAM_SCOPE));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPIImpl.Constants.PARAM_STATE));
    }


    @Test
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
                .generateAccessAndRefreshTokenFromInitial(orangeContext, initialToken);

        assertNotNull(orangeAccessToken);
        assertTrue("Missing access token", StringUtils.isNotEmpty(orangeAccessToken.getToken()));
        assertTrue("Refresh token was not recovered", StringUtils.isNotEmpty(orangeAccessToken.getRefreshToken().getToken()));
        assertTrue(orangeAccessToken.getExpirationTime().after(new Date()));
    }


}
