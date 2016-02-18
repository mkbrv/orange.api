package com.mkbrv.orange.identity;

import com.mkbrv.orange.identity.exception.OrangeIdentityException;

import java.util.Iterator;

/**
 * Created by mikibrv on 17/02/16.
 */
public class OrangeIdentityAPI {


    public static class Constants {
        static final String PARAM_RESPONSE_TYPE = "response_type";
        static final String PARAM_CLIENT_ID = "client_id";
        static final String PARAM_REDIRECT_URI = "redirect_uri";
        static final String PARAM_SCOPE = "scope";
        static final String PARAM_STATE = "state";
        static final String PARAM_PROMPT = "prompt";
    }


    /**
     * Builds the auth URL based on the orange context contained in the request;
     *
     * @param orangeContext uses the context inside;
     * @return URL
     */
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

    private String sanitizeUrl(final String url) {
        return url.replace("://", "%3A%2F%2F");
    }

    private void appendVariableToURL(final StringBuilder url, final String variable, final String value) {
        url.append("&").append(variable).append("=").append(value);
    }

    public void obtainAccessToken() {

    }

    public void generateAccessTokenFromRefreshToken() {

    }

}
