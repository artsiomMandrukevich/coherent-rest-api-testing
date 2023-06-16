package com.coherensolutions.rest.training.http;

import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

public class Client {

    @SneakyThrows
    public CloseableHttpResponse sendPost(CloseableHttpClient httpClient, String URL) {
        HttpPost httpPost = new HttpPost(URL);
        return httpClient.execute(httpPost);
    }

}
