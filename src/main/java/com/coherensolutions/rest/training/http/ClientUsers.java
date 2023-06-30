package com.coherensolutions.rest.training.http;

import com.coherensolutions.rest.training.dto.response.User;
import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import utils.Handler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClientUsers {

    private final Token token;
    private final Client client;
    PropertiesHelper props = new PropertiesHelper();

    ObjectMapper objectMapper = new ObjectMapper();

    public ClientUsers() {
        token = Token.getInstance();
        client = new Client();
    }

    private final String urlUsers = props.getAppProp().getProperty("api.url.users");

    @SneakyThrows
    public int sendPostUsers(User user) {
        CloseableHttpResponse response = client.sendPost(
                urlUsers,
                token.getWriteToken(),
                Handler.convertJsonIntoStringEntity(objectMapper.writeValueAsString(user))
        );
        int statusCode = response.getStatusLine().getStatusCode();
        response.close();
        return statusCode;
    }

    @SneakyThrows
    public List<User> sendGetUsers() {
        CloseableHttpResponse response = client.sendGet(urlUsers, token.getReadToken());
        assertEquals(200, response.getStatusLine().getStatusCode());
        String jsonBody = EntityUtils.toString(response.getEntity());
        List<User> listUser = objectMapper.readValue(jsonBody, new TypeReference<>() {});
        response.close();
        return listUser;
    }

    @SneakyThrows
    public List<User> sendGetUsers(GetKeyParameter keyParam, String valueParam) {
        CloseableHttpResponse response = client.sendGet(urlUsers, token.getReadToken(), keyParam, valueParam);
        assertEquals(200, response.getStatusLine().getStatusCode());
        String jsonBody = EntityUtils.toString(response.getEntity());
        List<User> listUser = objectMapper.readValue(jsonBody, new TypeReference<>() {});
        response.close();
        return listUser;
    }

}
