package com.mkbrv.orange.integration.identity;


import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.client.security.OrangeRefreshToken;
import com.mkbrv.orange.configuration.OrangeURLs;
import com.mkbrv.orange.identity.OrangeIdentityAPI;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;
import com.mkbrv.orange.identity.service.DefaultOrangeIdentityAPI;
import com.mkbrv.orange.integration.AbstractIntegrationTest;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;

import java.io.IOException;

/**
 * Created by mkbrv on 18/02/16.
 */
public class AbstractIdentityIntegrationTest extends AbstractIntegrationTest {

    /**
     *
     */
    protected OrangeIdentityAPI orangeIdentityAPI;

    /**
     *
     */
    protected OrangeIdentityContext orangeContext;

    /**
     * Keep a token in a static variable so we don't regenerate it at every test;
     */
    static OrangeAccessToken orangeAccessToken;


    @BeforeAll
    public void init() throws IOException {
        this.loadProperties();
        orangeContext = new OrangeIdentityContext();
        orangeContext.addScope(OrangeScope.cloudfullread).addScope(OrangeScope.offline_access)
                .addScope(OrangeScope.cloudfullwrite).addScope(OrangeScope.openid);
        orangeContext.addPrompt(OrangePrompt.login).addPrompt(OrangePrompt.consent);
        orangeContext.setOrangeURLs(OrangeURLs.DEFAULT)
                .setOrangeClientConfiguration(orangeClientConfiguration);
        this.orangeIdentityAPI = new DefaultOrangeIdentityAPI(this.orangeContext);
    }


    /**
     * An attempt to get an access token dynamically.
     * Not yet working.
     *
     * @return
     * @throws IOException
     */
    protected String obtainInitialAccessTokenForUser() throws IOException {
        return orangeIdentityAPI.buildOauthAuthorizeURL();
    }

    public OrangeAccessToken getOrangeAccessToken() {
        if (orangeAccessToken == null) {
            OrangeRefreshToken orangeRefreshToken = new OrangeRefreshToken(this.orangeAccountRefreshToken);
            orangeAccessToken = orangeIdentityAPI.generateAccessTokenFromRefreshToken(orangeRefreshToken);
        }
        return orangeAccessToken;
    }
}
