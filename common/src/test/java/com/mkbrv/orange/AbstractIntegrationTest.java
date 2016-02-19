package com.mkbrv.orange;

import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import org.junit.Before;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by mikibrv on 17/02/16.
 */
public abstract class AbstractIntegrationTest {

    protected OrangeClientConfiguration orangeClientConfiguration;

    protected String orangeAccountEmail;

    protected String orangeAccountPassword;

    protected String orangeAccountAccessToken;

    protected String orangeAccountRefreshToken;

    @Before
    public void loadProperties() throws IOException {

        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("../orange.keys.properties"));
            orangeClientConfiguration = new OrangeClientConfiguration(
                    properties.getProperty("orange.app.id"),
                    properties.getProperty("orange.client.id"),
                    properties.getProperty("orange.client.secret"),
                    properties.getProperty("orange.app.redirecturl")
            );

            this.orangeAccountEmail = properties.getProperty("orange.account.email");
            this.orangeAccountPassword = properties.getProperty("orange.account.password");
            this.orangeAccountAccessToken = properties.getProperty("orange.account.accesstoken");
            this.orangeAccountRefreshToken = properties.getProperty("orange.account.refreshtoken");

        } catch (Exception e) {
            //failed to load from here, try from System properties
            orangeClientConfiguration = new OrangeClientConfiguration(
                    System.getProperty("orange.app.id"),
                    System.getProperty("orange.client.secret"),
                    System.getProperty("orange.client.secret"),
                    System.getProperty("orange.app.redirecturl"));

            this.orangeAccountEmail = System.getProperty("orange.account.email");
            this.orangeAccountPassword = System.getProperty("orange.account.password");

            this.orangeAccountAccessToken = System.getProperty("orange.account.accesstoken");
            this.orangeAccountRefreshToken = System.getProperty("orange.account.refreshtoken");


            if (orangeClientConfiguration.getAppId() == null ||
                    orangeClientConfiguration.getAppRedirectURL() == null ||
                    orangeClientConfiguration.getClientSecret() == null) {
                throw new IOException(e);
            }
        }

    }


}
