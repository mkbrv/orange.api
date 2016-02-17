package com.mkbrv.orange.identity;

import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import com.mkbrv.orange.configuration.OrangeURLs;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mikibrv on 17/02/16.
 */
public class OrangeIdentityAPITest {

    final OrangeIdentityAPI orangeIdentityAPI = new OrangeIdentityAPI();

    private OrangeContext orangeContext;

    private OrangeClientConfiguration orangeClientConfiguration;

    @Before
    public void init() {
        orangeClientConfiguration = new OrangeClientConfiguration("appKey", "appSecret", "http://AppRedirect.com");
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
        orangeIdentityContext.addScope(OrangeScope.cloud).addScope(OrangeScope.offline_access);

        String authorizeUrl = orangeIdentityAPI.buildOauthAuthorizeURL(orangeIdentityContext);
        assertNotNull(authorizeUrl);
        assertTrue(authorizeUrl.length() > 0);
        assertTrue(authorizeUrl.contains(orangeClientConfiguration.getAppKey()));
        //the url is sanitized
        assertTrue(authorizeUrl.contains(orangeClientConfiguration.getAppRedirectURL().substring(10)));

        orangeIdentityContext.getOrangeScopeList()
                .forEach(orangeScope -> assertTrue(authorizeUrl.contains(orangeScope.toString())));


        //check all required params are there
        assertTrue(authorizeUrl.contains(OrangeIdentityAPI.Constants.PARAM_RESPONSE_TYPE));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPI.Constants.PARAM_CLIENT_ID));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPI.Constants.PARAM_REDIRECT_URI));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPI.Constants.PARAM_SCOPE));
        assertTrue(authorizeUrl.contains(OrangeIdentityAPI.Constants.PARAM_STATE));
    }


}
