package com.coherensolutions.rest.training.http;

import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Client {

    @SneakyThrows
    public CloseableHttpResponse sendPost(CloseableHttpClient httpClient, String URL) {
        HttpPost httpPost = new HttpPost(URL);
        return httpClient.execute(httpPost);
    }

    @SneakyThrows
    public CloseableHttpResponse sendGet(String URL, String bearerToken) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        return httpClient.execute(httpGet);
    }

    @SneakyThrows
    public CloseableHttpResponse sendPost(String URL, String bearerToken, StringEntity jsonBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setEntity(jsonBody);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return httpClient.execute(httpPost);
    }

}
