package com.coherensolutions.rest.training.http;

import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

public class Client {

    @SneakyThrows
    public CloseableHttpResponse sendPost(CloseableHttpClient httpClient, String URL) {
        HttpPost httpPost = new HttpPost(URL);
        return httpClient.execute(httpPost);
    }

    @SneakyThrows
    public CloseableHttpResponse sendGetBearerAuth(CloseableHttpClient httpClient, String URL, String bearerToken) {
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Authorization", "Bearer" + bearerToken);
        return httpClient.execute(httpGet);
    }

    @SneakyThrows
    public CloseableHttpResponse sendPostBearerAuth(CloseableHttpClient httpClient, String URL, String bearerToken, StringEntity jsonBody) {
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setEntity(jsonBody);
        httpPost.setHeader("Authorization", "Bearer" + bearerToken);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        return httpClient.execute(httpPost);
    }

}
