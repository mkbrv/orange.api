package com.mkbrv.orange.identity.integration;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests should not be run in the default maven goal
 * Created by mikibrv on 17/02/16.
 */
public class ITOrangeIdentityAPI extends AbstractIntegrationTest {


    @Test
    public void testFoundConfigurationFileWithSecretKey() {
        assertNotNull("You need to define a configuration file", this.orangeClientConfiguration);
    }
}
