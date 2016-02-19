package com.mkbrv.orange.identity.integration;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.mkbrv.orange.AbstractIntegrationTest;
import com.mkbrv.orange.client.OrangeContext;
import com.mkbrv.orange.configuration.OrangeURLs;
import com.mkbrv.orange.identity.OrangeIdentityAPI;
import com.mkbrv.orange.identity.impl.OrangeIdentityAPIImpl;
import com.mkbrv.orange.identity.model.OrangeIdentityContext;
import org.junit.Before;

import java.io.IOException;
import java.net.URL;

/**
 * Created by mikibrv on 18/02/16.
 */
public class AbstractIdentityIntegrationTest extends AbstractIntegrationTest {

    protected final OrangeIdentityAPI orangeIdentityAPI = new OrangeIdentityAPIImpl();

    protected OrangeContext orangeContext;


    @Before
    public void init() throws IOException {
        this.loadProperties();
        orangeContext = new OrangeContext()
                .setOrangeClientConfiguration(orangeClientConfiguration).setOrangeURLs(OrangeURLs.DEFAULT);
    }


    /**
     * An attempt to get an access token dynamically.
     * Not yet working.
     *
     * @param orangeIdentityContext
     * @return
     * @throws IOException
     */
    private String obtainInitialAccessTokenForUser(final OrangeIdentityContext orangeIdentityContext) throws IOException {
        String authorizeUrl = orangeIdentityAPI.buildOauthAuthorizeURL(orangeIdentityContext);

        final WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.waitForBackgroundJavaScript(30000);
        webClient.waitForBackgroundJavaScriptStartingBefore(30000);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCache().setMaxSize(0);
        CookieManager cookieManager = webClient.getCookieManager();
        cookieManager.setCookiesEnabled(true);

        final HtmlPage page = webClient.getPage(authorizeUrl);


        URL url = new URL("https://id.orange.fr/auth_user/bin/auth_user.cgi");


        WebRequest requestSettings = new WebRequest(url, HttpMethod.POST);

        requestSettings.setAdditionalHeader("Accept", "*/*");
        requestSettings.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestSettings.setAdditionalHeader("Referer", "REFURLHERE");
        requestSettings.setAdditionalHeader("Accept-Language", "en-US,en;q=0.8");
        requestSettings.setAdditionalHeader("Accept-Encoding", "gzip,deflate,sdch");
        requestSettings.setAdditionalHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
        requestSettings.setAdditionalHeader("X-Requested-With", "XMLHttpRequest");
        requestSettings.setAdditionalHeader("Cache-Control", "no-cache");
        requestSettings.setAdditionalHeader("Pragma", "no-cache");
        requestSettings.setAdditionalHeader("Origin", "https://YOURHOST");

        requestSettings.setRequestBody("-- removed due to security -- ");

        Page redirectPage = webClient.getPage(requestSettings);

        String redirectURL = ((HtmlPage) redirectPage).getBody().asText().substring(13);
        redirectURL = redirectURL.substring(0, redirectURL.length() - 3);

        webClient.getPage(redirectURL.replace("\\/", "/"));

        final HtmlForm form = page.getForms().get(0);

        final HtmlTextInput emailInput = form.getInputByName("credential");
        final HtmlPasswordInput pwdInput = form.getInputByName("password");

        emailInput.setValueAttribute(this.orangeAccountEmail);
        pwdInput.setValueAttribute(this.orangeAccountPassword);

        final HtmlSubmitInput submitInput = form.getInputByValue("s’identifier");
        final HtmlPage page2 = submitInput.click();


        return page2.toString();
    }


}
