package com.mkbrv.orange.identity.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mkbrv.orange.httpclient.ExceptionAwareHttpClient;
import com.mkbrv.orange.httpclient.OrangeHttpClient;
import com.mkbrv.orange.httpclient.SimpleHttpClient;
import com.mkbrv.orange.httpclient.request.OrangeRequest;
import com.mkbrv.orange.httpclient.response.OrangeResponse;
import com.mkbrv.orange.httpclient.security.OrangeAccessToken;
import com.mkbrv.orange.httpclient.security.OrangeAccessTokenHeader;
import com.mkbrv.orange.httpclient.security.OrangeRefreshToken;
import com.mkbrv.orange.identity.OrangeIdentityAPI;
import com.mkbrv.orange.identity.exception.OrangeIdentityException;
import com.mkbrv.orange.identity.model.AccessTokenDeserializer;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import com.mkbrv.orange.identity.model.OrangePrompt;
import com.mkbrv.orange.identity.model.OrangeScope;

import java.util.Iterator;

/**
 * {@inheritDoc}
 * Implementation of the OrangeIdentityAPI using GSON
 * Created by mkbrv on 17/02/16.
 */
public class DefaultOrangeIdentityAPI implements OrangeIdentityAPI {

    private final OrangeIdentityContext orangeContext;

    private final OrangeHttpClient orangeHttpClient;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(OrangeAccessToken.class,
            new AccessTokenDeserializer()).create();

    /**
     * @param orangeContext
     */
    public DefaultOrangeIdentityAPI(final OrangeIdentityContext orangeContext) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = new ExceptionAwareHttpClient(new SimpleHttpClient());
    }

    /**
     * @param orangeContext
     * @param orangeHttpClient
     */
    public DefaultOrangeIdentityAPI(final OrangeIdentityContext orangeContext, final OrangeHttpClient orangeHttpClient) {
        this.orangeContext = orangeContext;
        this.orangeHttpClient = orangeHttpClient;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String buildOauthAuthorizeURL() {
        StringBuilder url = new StringBuilder()
                .append(orangeContext.getOrangeURLs().getDomain())
                .append(orangeContext.getOrangeURLs().getOauthAuthorize());


        url.append("?").append(Constants.PARAM_RESPONSE_TYPE).append("=code");

        this.appendVariableToURL(url, Constants.PARAM_SCOPE, buildScope(this.orangeContext));
        this.appendVariableToURL(url, Constants.PARAM_CLIENT_ID,
                orangeContext.getOrangeClientConfiguration().getClientId());
        this.appendVariableToURL(url, Constants.PARAM_STATE,
                this.orangeContext.getState());
        this.appendVariableToURL(url, Constants.PARAM_PROMPT,
                this.buildPrompt(this.orangeContext));
        this.appendVariableToURL(url, Constants.PARAM_REDIRECT_URI,
                this.sanitizeUrl(orangeContext.getOrangeClientConfiguration().getAppRedirectURL()));

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


    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeAccessToken generateAccessAndRefreshTokenFromInitial(final OrangeAccessToken initialToken) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(orangeContext.getOrangeURLs().getOauthToken())
                .setOrangeAccessToken(initialToken)
                .setOrangeContext(orangeContext)
                .addHeader(OrangeAccessTokenHeader.HEADER_AUTHORIZATION,
                        new OrangeAccessTokenHeader(orangeContext).getHeaderValue())
                .addParameter(Constants.PARAM_GRANT_TYPE, Constants.PARAM_AUTH_CODE)
                .addParameter(Constants.PARAM_CODE, initialToken.getToken())
                .addParameter(Constants.PARAM_REDIRECT_URI, orangeContext.getOrangeClientConfiguration().getAppRedirectURL());

        OrangeResponse orangeResponse = orangeHttpClient.doPost(orangeRequest);
        return gson.fromJson(orangeResponse.getBody().toString(), OrangeAccessToken.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrangeAccessToken generateAccessTokenFromRefreshToken(final OrangeRefreshToken orangeRefreshToken) {
        OrangeRequest orangeRequest = new OrangeRequest()
                .setUrl(orangeContext.getOrangeURLs().getOauthToken())
                .addParameter(Constants.PARAM_GRANT_TYPE, Constants.PARAM_REFRESH_TOKEN)
                .addParameter(Constants.PARAM_REFRESH_TOKEN, orangeRefreshToken.getToken())
                //.addParameter(Constants.PARAM_SCOPE, this.buildScope(orangeContext)) fails - has to be checked
                .addParameter(Constants.PARAM_REDIRECT_URI, orangeContext.getOrangeClientConfiguration().getAppRedirectURL())
                .addHeader(OrangeAccessTokenHeader.HEADER_AUTHORIZATION,
                        new OrangeAccessTokenHeader(orangeContext).getHeaderValue());

        OrangeResponse orangeResponse = orangeHttpClient.doPost(orangeRequest);
        OrangeAccessToken orangeAccessToken = gson.fromJson(orangeResponse.getBody().toString(), OrangeAccessToken.class);
        orangeAccessToken.setRefreshToken(orangeRefreshToken);
        return orangeAccessToken;
    }

}
