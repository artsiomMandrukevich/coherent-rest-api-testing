package com.coherensolutions.rest.training.http;

import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import com.coherensolutions.rest.training.dto.response.*;
import utils.Handler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientZipCodes {

    private final Token token;
    private final Client client;
    private final Handler handler;
    PropertiesHelper props = new PropertiesHelper();

    public ClientZipCodes() {
        token = Token.getInstance();
        client = new Client();
        handler = new Handler();
    }

    private final String urlGetZipCodes = props.getAppProp().getProperty("api.url.get.zipcodes");
    private final String urlPostZipCodes = props.getAppProp().getProperty("api.url.post.zipcodes");

    @SneakyThrows
    public ZipCodesResponse sendGetZipCodes(int statusCode) {
        CloseableHttpResponse response = client.sendGet(urlGetZipCodes, token.getReadToken());
        return getListZipFromBody(response, statusCode);
    }

    @SneakyThrows
    public ZipCodesResponse sendPostZipCodes(List<String> listOfZipCodes, int statusCode) {
        CloseableHttpResponse response = client.sendPost(urlPostZipCodes, token.getWriteToken(), handler.convertListIntoJsonBody(listOfZipCodes));
        return getListZipFromBody(response, statusCode);
    }

    @SneakyThrows
    public ZipCodesResponse getListZipFromBody(CloseableHttpResponse response, int statusCode) {
        assertEquals(statusCode, response.getStatusLine().getStatusCode());
        ZipCodesResponse zipCodesResponse = new ZipCodesResponse(handler.getListFromResponse((response)));
        response.close();
        return zipCodesResponse;
    }

}
