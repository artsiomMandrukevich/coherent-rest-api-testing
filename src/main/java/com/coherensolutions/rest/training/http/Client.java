package com.coherensolutions.rest.training.http;

import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Client {

    @SneakyThrows
    public CloseableHttpResponse sendPost(CloseableHttpClient httpClient, String url) {
        HttpPost httpPost = new HttpPost(url);
        return httpClient.execute(httpPost);
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

    @SneakyThrows
    public CloseableHttpResponse sendGet(String url, String bearerToken) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        return httpClient.execute(httpGet);
    }

    @SneakyThrows
    public CloseableHttpResponse sendGet(String url, String bearerToken, GetKeyParameter keyParam, String valueParam) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        URI uri = new URIBuilder(httpGet.getURI()).addParameters(setQueryParameter(keyParam.value, valueParam))
                .build();
        httpGet.setURI(uri);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        return httpClient.execute(httpGet);
    }

    private List<NameValuePair> setQueryParameter(String keyParam, String valueParam) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        NameValuePair par = new BasicNameValuePair(keyParam, valueParam);
        nameValuePairs.add(par);
        return nameValuePairs;
    }

    @SneakyThrows
    public CloseableHttpResponse sendPut(String url, String bearerToken, StringEntity jsonBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(jsonBody);
        httpPut.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        httpPut.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPut.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return httpClient.execute(httpPut);
    }

    @SneakyThrows
    public CloseableHttpResponse sendPatch(String url, String bearerToken, StringEntity jsonBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setEntity(jsonBody);
        httpPatch.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        httpPatch.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPatch.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return httpClient.execute(httpPatch);
    }

    @SneakyThrows
    public CloseableHttpResponse sendDelete(String url, String bearerToken, StringEntity jsonBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        MyHttpDelete httpDelete = new MyHttpDelete(url);
        httpDelete.setEntity(jsonBody);
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        httpDelete.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpDelete.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return httpClient.execute(httpDelete);
    }

}
