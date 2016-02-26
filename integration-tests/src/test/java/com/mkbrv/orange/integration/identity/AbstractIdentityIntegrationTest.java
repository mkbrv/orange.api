package com.mkbrv.orange.integration.identity;


import com.mkbrv.orange.integration.AbstractIntegrationTest;
import com.mkbrv.orange.configuration.OrangeURLs;
import com.mkbrv.orange.identity.OrangeIdentityAPI;
import com.mkbrv.orange.identity.impl.OrangeIdentityAPIImpl;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;
import org.junit.Before;

import java.io.IOException;

/**
 * Created by mikibrv on 18/02/16.
 */
public class AbstractIdentityIntegrationTest extends AbstractIntegrationTest {

    protected OrangeIdentityAPI orangeIdentityAPI;

    protected OrangeIdentityContext orangeContext;


    @Before
    public void init() throws IOException {
        this.loadProperties();
        orangeContext = new OrangeIdentityContext();
        orangeContext.addScope(OrangeScope.cloudfullread).addScope(OrangeScope.offline_access);
        orangeContext.addPrompt(OrangePrompt.login);
        orangeContext.setOrangeURLs(OrangeURLs.DEFAULT)
                .setOrangeClientConfiguration(orangeClientConfiguration);
        this.orangeIdentityAPI = new OrangeIdentityAPIImpl(this.orangeContext);
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

}