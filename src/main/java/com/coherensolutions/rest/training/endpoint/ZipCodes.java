package com.coherensolutions.rest.training.endpoint;

import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import com.coherensolutions.rest.training.http.Client;
import com.coherensolutions.rest.training.http.Token;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.coherensolutions.rest.training.dto.response.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZipCodes {

    Token token = Token.getInstance();
    Client client = new Client();
    static ObjectMapper objectMapper = new ObjectMapper();

    private final String urlGetZipCodes = PropertiesHelper.getAppProperties().getProperty("api.url.get.zipcodes");
    private final String urlPostZipCodes = PropertiesHelper.getAppProperties().getProperty("api.url.post.zipcodes");
    private final String[] expectedAvailableListOfZipCodes = PropertiesHelper.getAppProperties().getProperty("expected.available.list.zipcodes").split(",");
    private final String[] expandedListOfZipCodes = PropertiesHelper.getAppProperties().getProperty("expanded.list.zipcodes").split(",");
    private final String[] duplicatiedAvailableListOfZipCodes = PropertiesHelper.getAppProperties().getProperty("duplicated.available.list.zipcodes").split(",");
    private final String[] duplicatiedUsedListOfZipCodes = PropertiesHelper.getAppProperties().getProperty("duplicated.used.zipcodes").split(",");

    public List<String> getListOfZipCodesByType(TypeListOfZipCodes typeListOfZipCodes) {
        List<String> list = null;
        switch (typeListOfZipCodes) {
            case EXPECTED_AVAILABLE -> list = Arrays.asList(expectedAvailableListOfZipCodes);
            case EXPANDED -> list = Arrays.asList(expandedListOfZipCodes);
            case DUPLICATED_AVAILABLE -> list = Arrays.asList(duplicatiedAvailableListOfZipCodes);
            case DUPLICATED_USED -> list = Arrays.asList(duplicatiedUsedListOfZipCodes);
            default -> System.out.println("Oooops, something wrong !");
        }
        return list;
    }

    public boolean areThereDuplicateZipCodes(List<String> responseZipCodes, List<String> addedZipCodes) {
        int countOfZipcode = 0;
        for (String str : addedZipCodes) {
            countOfZipcode = countOfZipcode + Collections.frequency(responseZipCodes, str);
        }
        // this method will return false in the case when
        // there is duplicate zipCode from the addedZipCodes list in the responseZipCodes list
        return countOfZipcode == addedZipCodes.size();
    }

    @SneakyThrows
    private StringEntity convertListIntoJsonBody(List<String> listBody) {
        return new StringEntity(listBody.toString());
    }

    @SneakyThrows
    private int getStatusFromResponse(CloseableHttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    @SneakyThrows
    private List<String> getZipCodesFromResponse(CloseableHttpResponse response) {
        String jsonBody = EntityUtils.toString(response.getEntity());
        return objectMapper.readValue(jsonBody, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public ZipCodesResponse sendGetZipCodes() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = client.sendGetBearerAuth(httpclient, urlGetZipCodes, token.getReadToken());
        ZipCodesResponse zipCodesResponse = new ZipCodesResponse(getStatusFromResponse(response), getZipCodesFromResponse(response));
        httpclient.close();
        return zipCodesResponse;
    }

    @SneakyThrows
    public ZipCodesResponse sendPostZipCodes(TypeListOfZipCodes typeListOfZipCodes) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = client.sendPostBearerAuth(httpclient, urlPostZipCodes, token.getWriteToken(), convertListIntoJsonBody(getListOfZipCodesByType(typeListOfZipCodes)));
        ZipCodesResponse zipCodesResponse = new ZipCodesResponse(getStatusFromResponse(response), getZipCodesFromResponse(response));
        httpclient.close();
        return zipCodesResponse;
    }

}
