package com.mkbrv.orange.integration.identity;

import com.mkbrv.orange.client.exception.OrangeException;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.client.security.OrangeRefreshToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;


/**
 * Integration tests should not be run in the default maven goal
 * Created by mkbrv on 17/02/16.
 */
public class ITOrangeIdentityAPI extends AbstractIdentityIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ITOrangeIdentityAPI.class);


    @Test
    public void testFoundConfigurationFileWithSecretKey() {
        assertNotNull(this.orangeClientConfiguration, "You need to define a configuration file");
    }

    @BeforeAll
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

        LOG.warn("AuthorizationURL obtained for generating a real token : {}", authorizeUrl);
    }


    @Test
    public void canObtainInitialAccessToken() throws IOException {
        String accessToken = this.obtainInitialAccessTokenForUser();
        assertNotNull(accessToken);
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

            LOG.warn("Access Token obtained from the initial token: {} ", orangeAccessToken.toString());
        } catch (OrangeException e) {
            //Initial token has expired, we cannot generate a new one
            assertEquals(new Integer(400), e.getOrangeResponse().getStatus());
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
        LOG.info("Generated token using refresh: {}", orangeAccessToken);
    }

}
