package com.coherensolutions.rest.training.restassured;

import com.coherensolutions.rest.training.helpers.PropertiesHelper;

import static io.restassured.RestAssured.given;

public class RaClientBasicAuth {

    PropertiesHelper props = new PropertiesHelper();
    String defaultUser = props.getAppProp().getProperty("api.user");
    String defaultPassArray = props.getAppProp().getProperty("api.password");

    public String getOauthToken(String url) {
        return given().auth().basic(defaultUser, defaultPassArray)
                .when().post(url)
                .then().extract().path("access_token");
    }

}
