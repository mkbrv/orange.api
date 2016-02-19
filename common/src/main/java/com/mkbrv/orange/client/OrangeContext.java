package com.mkbrv.orange.client;

import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import com.mkbrv.orange.configuration.OrangeURLs;

/**
 * Stores default configuration used in the other modules.
 * The configuration can be changed, even at runtime;
 * Created by mikibrv on 17/02/16.
 */
public class OrangeContext {

    private OrangeClientConfiguration orangeClientConfiguration;

    private OrangeURLs orangeURLs;

    public OrangeClientConfiguration getOrangeClientConfiguration() {
        return orangeClientConfiguration;
    }

    public OrangeContext setOrangeClientConfiguration(OrangeClientConfiguration orangeClientConfiguration) {
        this.orangeClientConfiguration = orangeClientConfiguration;
        return this;
    }

    public OrangeURLs getOrangeURLs() {
        if (this.orangeURLs == null) {
            this.orangeURLs = OrangeURLs.DEFAULT;
        }
        return this.orangeURLs;
    }

    public OrangeContext setOrangeURLs(final OrangeURLs orangeURLs) {
        this.orangeURLs = orangeURLs;
        return this;
    }
}
