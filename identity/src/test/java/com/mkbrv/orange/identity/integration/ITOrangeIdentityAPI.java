package com.mkbrv.orange.identity.integration;

import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.client.security.OrangeRefreshToken;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
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
        String authorizeUrl = orangeIdentityAPI.buildOauthAuthorizeURL();
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
        OrangeIdentityContext orangeIdentityContext = new OrangeIdentityContext();
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

        try {
            OrangeAccessToken initialToken = new OrangeAccessToken(this.orangeAccountAccessToken);
            OrangeAccessToken orangeAccessToken = orangeIdentityAPI.generateAccessAndRefreshTokenFromInitial(initialToken);
            assertNotNull(orangeAccessToken);

            System.out.println("Access Token: " + orangeAccessToken.toString());
        } catch (OrangeException e) {
            //Initial token has expired, we cannot generate a new one
            assertEquals(new Integer(400), e.getCode());
            assertTrue(e.getMessage().contains("invalid_grant"));
        }
    }

    @Test
    public void canGenerateAccessTokenFromRefreshToken() {
        //we were unable to generate this dynamically based on user & pwd. so we can only use temporary ones
        if (this.orangeAccountRefreshToken == null || this.orangeAccountRefreshToken.length() == 0) {
            return;
        }

        OrangeRefreshToken orangeRefreshToken = new OrangeRefreshToken(this.orangeAccountRefreshToken);
        OrangeAccessToken orangeAccessToken = orangeIdentityAPI.generateAccessTokenFromRefreshToken(orangeRefreshToken);


        assertNotNull(orangeAccessToken);
        assertNotNull(orangeAccessToken.getRefreshToken());
        assertTrue(StringUtils.isNotEmpty(orangeAccessToken.getToken()));
        assertTrue(orangeAccessToken.getExpirationTime().after(new Date()));
        System.out.println("Generated token using refresh: " + orangeAccessToken);
    }

}
