package com.mkbrv.orange.identity.integration;

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

    @Before
    public void loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("../orange.keys.properties"));
        orangeClientConfiguration = new OrangeClientConfiguration(
                properties.getProperty("orange.app.key"),
                properties.getProperty("orange.app.secret"),
                properties.getProperty("orange.app.redirecturl")
        );

    }


}
