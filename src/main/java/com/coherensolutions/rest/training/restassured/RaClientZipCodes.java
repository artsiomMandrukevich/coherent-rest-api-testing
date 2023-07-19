package com.coherensolutions.rest.training.restassured;

import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import lombok.SneakyThrows;

import java.util.List;

public class RaClientZipCodes {

    private final RaToken raToken;
    private final RaClient raClient;
    PropertiesHelper props = new PropertiesHelper();

    public RaClientZipCodes() {
        raToken = RaToken.getInstance();
        raClient = new RaClient();
    }

    private final String urlGetZipCodes = props.getAppProp().getProperty("api.url.get.zipcodes");
    private final String urlPostZipCodes = props.getAppProp().getProperty("api.url.post.zipcodes");

    @SneakyThrows
    @Step("Get available zipCodes from app")
    public List<String> raSendGetZipCodes(int statusCode) {
        return raClient.sendGet(urlGetZipCodes, raToken.getReadToken(), statusCode).as(new TypeRef<>() {});
    }

    @SneakyThrows
    @Step("Create new zipCodes in app")
    public void raSendPostZipCodes(List<String> listOfZipCodes, int statusCode) {
        raClient.sendPost(urlPostZipCodes, raToken.getWriteToken(), statusCode, listOfZipCodes.toString());
    }

}
