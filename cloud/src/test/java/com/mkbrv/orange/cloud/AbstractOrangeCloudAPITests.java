package com.mkbrv.orange.cloud;

import com.mkbrv.orange.httpclient.OrangeContext;
import com.mkbrv.orange.httpclient.OrangeHttpClient;
import com.mkbrv.orange.cloud.service.DefaultOrangeCloudFoldersAPI;
import com.mkbrv.orange.configuration.OrangeClientConfiguration;
import org.junit.gen5.api.BeforeEach;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mkbrv on 03/03/16.
 */
public class AbstractOrangeCloudAPITests {

    protected OrangeCloudFoldersAPI orangeCloudFoldersAPI;
    protected OrangeHttpClient orangeHttpClient;
    protected OrangeContext orangeContext;

    protected final OrangeClientConfiguration orangeClientConfiguration
            = new OrangeClientConfiguration("appId", "clientID", "clientSecret", "http://app.com");

    @BeforeEach @org.junit.Before
    public void init() throws IOException {
        orangeContext = new OrangeContext().setOrangeClientConfiguration(this.orangeClientConfiguration);
        orangeHttpClient = Mockito.mock(OrangeHttpClient.class);
        orangeCloudFoldersAPI = new DefaultOrangeCloudFoldersAPI(orangeContext, orangeHttpClient);
    }

    /**
     * Reads a json from the resources folder containing a mocked response from one of orange api calls;
     *
     * @param fileName
     * @return
     */
    protected String readResponseAsJson(final String fileName) {
        String result = "";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
