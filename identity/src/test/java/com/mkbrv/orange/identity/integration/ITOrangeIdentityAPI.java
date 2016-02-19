package com.mkbrv.orange.identity.integration;

import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Integration tests should not be run in the default maven goal
 * Created by mikibrv on 17/02/16.
 */
public class ITOrangeIdentityAPI extends AbstractIdentityIntegrationTest {

    @Test
    public void testFoundConfigurationFileWithSecretKey() {
        assertNotNull("You need to define a configuration file", this.orangeClientConfiguration);
    }

    @Before
    public void init() throws IOException {
        super.init();
    }


    @Test
    public void canBuildValidOrangeOauthURL() throws IOException {
        OrangeIdentityContext orangeIdentityContext = new OrangeIdentityContext(orangeContext);
        orangeIdentityContext.addPrompt(OrangePrompt.login);
        orangeIdentityContext.addScope(OrangeScope.cloudfullread).addScope(OrangeScope.offline_access);
        String authorizeUrl = orangeIdentityAPI.buildOauthAuthorizeURL(orangeIdentityContext);
        assertNotNull(authorizeUrl);
        HttpGet callAuthorizeUrl = new HttpGet(authorizeUrl);
        HttpResponse response = HttpClientBuilder.create().build().execute(callAuthorizeUrl);
        //answer from orange must be 2xx or 3xx (redirect - cached)
        assertTrue(response.getStatusLine().getStatusCode() < 400);
        assertTrue(response.getStatusLine().getStatusCode() >= 200);

        System.out.println("AuthorizationURL : " + authorizeUrl);
    }


    @Test
    public void canObtainInitialAccessToken() throws IOException {
        OrangeIdentityContext orangeIdentityContext = new OrangeIdentityContext(orangeContext);
        orangeIdentityContext.addPrompt(OrangePrompt.login);
        orangeIdentityContext.addScope(OrangeScope.cloudfullread).addScope(OrangeScope.offline_access);
        //String accessToken = this.obtainInitialAccessTokenForUser(orangeIdentityContext);
    }

    @Test
    public void canGenerateAccessTokenAndRefreshToken() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountAccessToken == null || this.orangeAccountAccessToken.length() == 0) {
            return;
        }

        OrangeAccessToken initialToken = new OrangeAccessToken(this.orangeAccountAccessToken);
        OrangeAccessToken orangeAccessToken = orangeIdentityAPI.generateAccessAndRefreshTokenFromInitial(orangeContext, initialToken);
        assertNotNull(orangeAccessToken);

        System.out.println("Access Token: " + orangeAccessToken.toString());
    }

}