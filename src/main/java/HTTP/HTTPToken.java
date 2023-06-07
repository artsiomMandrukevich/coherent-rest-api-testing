package HTTP;
import lombok.SneakyThrows;
import org.apache.http.impl.client.CloseableHttpClient;

import static HTTP.HTTPVariables.URL_FOR_READ_TOKEN;
import static HTTP.HTTPVariables.URL_FOR_WRITE_TOKEN;

public class HTTPToken {

    private static String writeToken;
    private static String readToken;

    HTTPBasicAuthClient httpBasicAuthClient = new HTTPBasicAuthClient();

    @SneakyThrows
    private String getOauthToken(CloseableHttpClient httpClient, String URL) {
        HTTPResponseDTO httpResponseDTO = httpBasicAuthClient.callPostReturnHTTPResponseData(httpClient, URL);
        return httpResponseDTO.getBody().getString("access_token");
    }

    @SneakyThrows
    private HTTPToken() {
        CloseableHttpClient httpClient = httpBasicAuthClient.getHttpClientWithBasicAuthCredentials();
        writeToken = getOauthToken(httpClient, URL_FOR_WRITE_TOKEN);
        readToken = getOauthToken(httpClient, URL_FOR_READ_TOKEN);
        httpClient.close();
    }

    private static class SingletoneHelper {
        private static final HTTPToken httpTokenInstance = new HTTPToken();
    }

    public static HTTPToken getInstance() {
        return SingletoneHelper.httpTokenInstance;
    }

    public String getWriteToken() {
        return writeToken;
    }

    public String getReadToken() {
        return readToken;
    }

}
