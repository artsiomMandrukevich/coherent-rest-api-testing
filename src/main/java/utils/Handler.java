package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class Handler {

    @SneakyThrows
    public static StringEntity convertListIntoStringEntity(List<String> listBody) {
        return new StringEntity(listBody.toString());
    }

    @SneakyThrows
    public static StringEntity convertJsonIntoStringEntity(String json) {
        return new StringEntity(json);
    }

    @SneakyThrows
    public static List<String> getListFromResponse(CloseableHttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = EntityUtils.toString(response.getEntity());
        return objectMapper.readValue(jsonBody, new TypeReference<>() {
        });
    }

}
