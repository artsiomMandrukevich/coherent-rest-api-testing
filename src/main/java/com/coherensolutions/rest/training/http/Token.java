package com.coherensolutions.rest.training.http;
import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import lombok.SneakyThrows;

public class Token {

    private static String writeToken;
    private static String readToken;

    String urlForWriteToken = PropertiesHelper.getAppProperties().getProperty("api.url.write");
    String urlForReadToken = PropertiesHelper.getAppProperties().getProperty("api.url.read");

    ClientBasicAuth clientBasicAuth = new ClientBasicAuth();

    @SneakyThrows
    private Token() {
        writeToken = clientBasicAuth.getOauthToken(urlForWriteToken);
        readToken = clientBasicAuth.getOauthToken(urlForReadToken);
    }

    private static class SingletoneHelper {
        private static final Token TOKEN_INSTANCE = new Token();
    }

    public static Token getInstance() {
        return SingletoneHelper.TOKEN_INSTANCE;
    }

    public String getWriteToken() {
        return writeToken;
    }

    public String getReadToken() {
        return readToken;
    }

}
