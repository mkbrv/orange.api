package com.mkbrv.orange.identity.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.client.OrangeHttpClient;
import com.mkbrv.orange.client.SimpleHttpClient;
import com.mkbrv.orange.client.request.OrangeRequest;
import com.mkbrv.orange.client.response.OrangeResponse;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.configuration.OrangeURLs;
import com.mkbrv.orange.identity.OrangeIdentityAPI;
import com.mkbrv.orange.identity.exception.OrangeIdentityException;
import com.mkbrv.orange.identity.model.OrangeAccessTokenDeserializer;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;
import org.apache.commons.codec.binary.Base64;

import java.util.Iterator;

/**
 * Created by mikibrv on 17/02/16.
 */
public class OrangeIdentityAPIImpl implements OrangeIdentityAPI {

    private OrangeHttpClient orangeHttpClient;

    private Gson gson = new GsonBuilder().registerTypeAdapter(OrangeAccessToken.class,
            new OrangeAccessTokenDeserializer()).create();

    /**
     *
     */
    public OrangeIdentityAPIImpl() {
        this.orangeHttpClient = new SimpleHttpClient();
    }

    /**
     * @param orangeHttpClient
     */
    public OrangeIdentityAPIImpl(final OrangeHttpClient orangeHttpClient) {
        this.orangeHttpClient = orangeHttpClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String buildOauthAuthorizeURL(final OrangeIdentityContext orangeContext) {
        StringBuilder url = new StringBuilder()
                .append(orangeContext.getOrangeContext().getOrangeURLs().getDomain())
                .append(orangeContext.getOrangeContext().getOrangeURLs().getOauthAuthorize());


        url.append("?").append(Constants.PARAM_RESPONSE_TYPE).append("=code");

        this.appendVariableToURL(url, Constants.PARAM_SCOPE, buildScope(orangeContext));
        this.appendVariableToURL(url, Constants.PARAM_CLIENT_ID,
                orangeContext.getOrangeContext().getOrangeClientConfiguration().getClientId());
        this.appendVariableToURL(url, Constants.PARAM_STATE,
                orangeContext.getState());
        this.appendVariableToURL(url, Constants.PARAM_PROMPT,
                this.buildPrompt(orangeContext));
        this.appendVariableToURL(url, Constants.PARAM_REDIRECT_URI,
                this.sanitizeUrl(orangeContext.getOrangeContext().getOrangeClientConfiguration().getAppRedirectURL()));

        return url.toString();
    }

    /**
     * Builds the Prompt param of the GET Request
     *
     * @param orangeIdentityContext
     * @return
     */
    private String buildPrompt(final OrangeIdentityContext orangeIdentityContext) {
        if (orangeIdentityContext.getPromptList().isEmpty()) {
            throw new OrangeIdentityException("At least one prompt required for oauth");
        }
        StringBuilder promptBuilder = new StringBuilder();
        Iterator<OrangePrompt> scopeIterator = orangeIdentityContext.getPromptList().listIterator();
        while (scopeIterator.hasNext()) {
            promptBuilder.append(scopeIterator.next().toString());
            if (scopeIterator.hasNext()) {
                promptBuilder.append("%20");
            }
        }
        return promptBuilder.toString();
    }

    /**
     * Builds the scope param of the GET request
     *
     * @param orangeContext
     * @return
     */
    private String buildScope(final OrangeIdentityContext orangeContext) {
        if (orangeContext.getOrangeScopeList().isEmpty()) {
            throw new OrangeIdentityException("At least one scope required for oauth");
        }
        StringBuilder scopeBuilder = new StringBuilder();
        Iterator<OrangeScope> scopeIterator = orangeContext.getOrangeScopeList().listIterator();
        while (scopeIterator.hasNext()) {
            scopeBuilder.append(scopeIterator.next().toString());
            if (scopeIterator.hasNext()) {
                scopeBuilder.append("%20");
            }
        }
        return scopeBuilder.toString();
    }

    /**
     * Sanitizez the http as requested by Orange API
     *
     * @param url
     * @return
     */
    private String sanitizeUrl(final String url) {
        return url.replace("://", "%3A%2F%2F");
    }

    /**
     * Appends a param to the URL;
     *
     * @param url
     * @param variable
     * @param value
     */
    private void appendVariableToURL(final StringBuilder url, final String variable, final String value) {
        url.append("&").append(variable).append("=").append(value);
    }


    @Override
    public OrangeAccessToken generateAccessAndRefreshTokenFromInitial(final OrangeContext orangeContext,
                                                                      final OrangeAccessToken initialToken) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(orangeContext.getOrangeURLs().getOauthToken())
                .setOrangeAccessToken(initialToken)
                .setOrangeContext(orangeContext)
                .addHeader("Authorization", "Basic " + new String(Base64.encodeBase64(
                        (orangeContext.getOrangeClientConfiguration().getClientId() + ":"
                                + orangeContext.getOrangeClientConfiguration().getClientSecret()).getBytes())))
                .addParameter("grant_type", "authorization_code")
                .addParameter("code", initialToken.getToken())
                .addParameter("redirect_uri", orangeContext.getOrangeClientConfiguration().getAppRedirectURL());

        OrangeResponse orangeResponse = orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeAccessToken.class);
    }

    @Override
    public void generateAccessTokenFromRefreshToken() {

    }

}
