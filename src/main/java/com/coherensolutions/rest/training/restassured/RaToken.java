package com.coherensolutions.rest.training.restassured;

import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import lombok.SneakyThrows;

public class RaToken {

    private static String writeToken;
    private static String readToken;
    PropertiesHelper props = new PropertiesHelper();

    String urlForWriteToken = props.getAppProp().getProperty("api.url.write");
    String urlForReadToken = props.getAppProp().getProperty("api.url.read");

    RaClientBasicAuth raClientBasicAuth = new RaClientBasicAuth();

    @SneakyThrows
    private RaToken() {
        writeToken = raClientBasicAuth.getOauthToken(urlForWriteToken);
        readToken = raClientBasicAuth.getOauthToken(urlForReadToken);
    }

    private static class SingletoneHelper {
        private static final RaToken TOKEN_INSTANCE = new RaToken();
    }

    public static RaToken getInstance() {
        return RaToken.SingletoneHelper.TOKEN_INSTANCE;
    }

    public String getWriteToken() {
        return writeToken;
    }

    public String getReadToken() {
        return readToken;
    }

}
