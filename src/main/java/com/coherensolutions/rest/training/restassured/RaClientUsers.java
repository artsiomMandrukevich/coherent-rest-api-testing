package com.coherensolutions.rest.training.restassured;

import com.coherensolutions.rest.training.dto.response.User;
import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import com.coherensolutions.rest.training.http.GetKeyParameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import utils.Handler;

import java.util.List;

public class RaClientUsers {

    private final RaToken raToken;
    private final RaClient raClient;
    PropertiesHelper props = new PropertiesHelper();

    ObjectMapper objectMapper = new ObjectMapper();

    public RaClientUsers() {
        raToken = RaToken.getInstance();
        raClient = new RaClient();
    }

    private final String urlUsers = props.getAppProp().getProperty("api.url.users");
    private final String urlUsersUpload = props.getAppProp().getProperty("api.url.users.upload");

    @SneakyThrows
    @Step("Get available Users from app")
    public List<User> raSendGetUsers(int statusCode) {
        return raClient.sendGet(urlUsers, raToken.getReadToken(), statusCode).as(new TypeRef<>() {});
    }

    @SneakyThrows
    @Step("Get available Users from app")
    public List<User> raSendGetUsers(int statusCode, GetKeyParameter keyParam, String valueParam) {
        return raClient.sendGet(urlUsers
                , raToken.getReadToken()
                , statusCode
                , keyParam.value
                , valueParam
        ).as(new TypeRef<>() {});
    }


    @SneakyThrows
    @Step("Create new Users in app")
    public void raSendPostUsers(User user, int statusCode) {
        raClient.sendPost(urlUsers
                , raToken.getWriteToken()
                , statusCode
                , objectMapper.writeValueAsString(user)
        );
    }

    @SneakyThrows
    @Step("Delete users from app")
    public void raSendDeleteUsers(User user, int statusCode) {
        raClient.sendDelete(urlUsers
                , raToken.getWriteToken()
                , statusCode
                , objectMapper.writeValueAsString(user)
        );
    }

    @SneakyThrows
    @Step("Change User through PUT")
    public void raSendPutUsers(User userToChange, User userToUpdate, int statusCode) {
        raClient.sendPut(urlUsers
                , raToken.getWriteToken()
                , statusCode
                , Handler.convertUsersIntoJsonForPutPatch(userToChange, userToUpdate)
        );
    }

    @SneakyThrows
    @Step("Change User through PATCH")
    public void raSendPatchUsers(User userToChange, User userToUpdate, int statusCode) {
        raClient.sendPatch(urlUsers
                , raToken.getWriteToken()
                , statusCode
                , Handler.convertUsersIntoJsonForPutPatch(userToChange, userToUpdate)
        );
    }

    @SneakyThrows
    @Step("Add Users in app through the Upload endpoint")
    public String raSendPostUploadUsers(User userFirst, User userSecond, int statusCode) {
        Response response = raClient.sendPostMultipartUpload(urlUsersUpload
                , raToken.getWriteToken()
                , statusCode
                , Handler.convertStringIntoBytes(Handler.convertUsersIntoJsonStringForPostUpload(userFirst, userSecond))
        );
        return response.getBody().asString();
    }

}

