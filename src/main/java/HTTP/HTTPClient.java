package HTTP;

import JSON.JSONHelper;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

public class HTTPClient {

    @SneakyThrows
    public HTTPResponseDTO callPostReturnHTTPResponseData(CloseableHttpClient httpClient, String URL) {

        HTTPResponseDTO HTTPResponseDTO = new HTTPResponseDTO();
        JSONHelper jsonHelper = new JSONHelper();

        HttpPost httpPost = new HttpPost(URL);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HTTPResponseDTO.setStatus(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
        try {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                HTTPResponseDTO.setBody(jsonHelper.convertResponseBodyIntoJSON(entity));
            }
        } finally {
            httpResponse.close();
        }
        return HTTPResponseDTO;
    }

}
