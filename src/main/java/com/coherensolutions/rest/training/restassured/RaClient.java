package com.coherensolutions.rest.training.restassured;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RaClient {

    public Response sendGet(String url, String bearerToken, int statusCode) {
        return given().auth().oauth2(bearerToken)
                .when().get(url)
                .then().statusCode(statusCode)
                .extract().response();
    }

    public Response sendGet(String url, String bearerToken, int statusCode, String keyParam, String valueParam) {
        return given().auth().oauth2(bearerToken)
                .param(keyParam, valueParam)
                .when().get(url)
                .then().statusCode(statusCode)
                .extract().response();
    }

    public void sendPost(String url, String bearerToken, int statusCode, String json) {
        Allure.addAttachment("Payload:", json);
        given().auth().oauth2(bearerToken).contentType("application/json").body(json)
                .when().post(url)
                .then().statusCode(statusCode);
    }

    public void sendDelete(String url, String bearerToken, int statusCode, String json) {
        Allure.addAttachment("Payload:", json);
        given().auth().oauth2(bearerToken).contentType("application/json").body(json)
                .when().delete(url)
                .then().statusCode(statusCode);
    }

    public void sendPut(String url, String bearerToken, int statusCode, String json) {
        Allure.addAttachment("Payload:", json);
        given().auth().oauth2(bearerToken).contentType("application/json").body(json)
                .when().put(url)
                .then().statusCode(statusCode);
    }

    public void sendPatch(String url, String bearerToken, int statusCode, String json) {
        Allure.addAttachment("Payload:", json);
        given().auth().oauth2(bearerToken).contentType("application/json").body(json)
                .when().patch(url)
                .then().statusCode(statusCode);
    }

    public Response sendPostMultipartUpload(String url, String bearerToken, int statusCode, byte[] message) {
        Allure.addAttachment("Payload:", new String(message));
        return given().auth().oauth2(bearerToken)
                .multiPart("file", "TEXTFILENAME", message)
                .when().post(url)
                .then().statusCode(statusCode)
                .extract().response();
    }

}
