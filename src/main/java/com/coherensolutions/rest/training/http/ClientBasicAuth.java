package com.coherensolutions.rest.training.http;

import com.coherensolutions.rest.training.dto.response.OauthToken;
import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ClientBasicAuth extends Client {

    Client client = new Client();
    static ObjectMapper objectMapper = new ObjectMapper();
    PropertiesHelper props = new PropertiesHelper();


    String hostName = props.getAppProp().getProperty("api.host.name");
    String scheme = props.getAppProp().getProperty("api.scheme");
    String defaultUser = props.getAppProp().getProperty("api.user");
    String defaultPassArray = props.getAppProp().getProperty("api.password");
    int port = Integer.parseInt(props.getAppProp().getProperty("api.port"));

    private CloseableHttpClient getClientWithBasicAuthCredentials() {
        return HttpClientBuilder.create().setDefaultCredentialsProvider(getBasiAuthCredentials()).build();
    }

    private BasicCredentialsProvider getBasiAuthCredentials() {
        final HttpHost targetHost = new HttpHost(hostName, port, scheme);
        final BasicCredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(targetHost);
        provider.setCredentials(authScope, new UsernamePasswordCredentials(defaultUser, defaultPassArray));
        return provider;
    }

    private OauthToken parsePostOathToken(CloseableHttpResponse httpResponse) throws IOException {
        String jsonBody = EntityUtils.toString(httpResponse.getEntity());
        return objectMapper.readValue(jsonBody, OauthToken.class);
    }

    @SneakyThrows
    public String getOauthToken(String URL) {
        CloseableHttpClient httpClient = getClientWithBasicAuthCredentials();
        OauthToken oathToken = parsePostOathToken(client.sendPost(httpClient, URL));
        httpClient.close();
        return oathToken.getAccessToken();
    }

}
