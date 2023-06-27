package com.coherensolutions.rest.training.http;

import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
    public CloseableHttpResponse sendGet(String url, String bearerToken, String keyParam, String valueParam) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        URI uri = new URIBuilder(httpGet.getURI()).addParameters(setQueryParameter(keyParam, valueParam))
                .build();
        httpGet.setURI(uri);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + bearerToken);
        return httpClient.execute(httpGet);
    }

    public List<NameValuePair> setQueryParameter(String parameter, String value) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        NameValuePair par = new BasicNameValuePair(parameter, value);
        nameValuePairs.add(par);
        return nameValuePairs;
    }

}
