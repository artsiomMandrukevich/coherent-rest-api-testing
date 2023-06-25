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
    public CloseableHttpResponse sendPost(CloseableHttpClient httpClient, String url) {
        HttpPost httpPost = new HttpPost(url);
        return httpClient.execute(httpPost);
    }

    @SneakyThrows
    public CloseableHttpResponse sendGet(String url, String bearerToken) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        return httpClient.execute(httpGet);
    }

    @SneakyThrows
    public CloseableHttpResponse sendPost(String url, String bearerToken, StringEntity jsonBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(jsonBody);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return httpClient.execute(httpPost);
    }

}
