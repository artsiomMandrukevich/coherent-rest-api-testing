package utils;

import com.coherensolutions.rest.training.dto.response.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @SneakyThrows
    public static String convertUsersIntoJsonForPutPatch(User userToChange, User userToUpdate) {
        Map<String, User> payloadPath = new HashMap<>();
        payloadPath.put("userNewValues",userToUpdate);
        payloadPath.put("userToChange",userToChange);
        return new ObjectMapper().writeValueAsString(payloadPath);
    }

    @SneakyThrows
    public static String convertUsersIntoJsonStringForPostUpload(User userFirst, User userSecond) {
        List<User> payloadPath = new ArrayList<>();
        payloadPath.add(userFirst);
        payloadPath.add(userSecond);
        return new ObjectMapper().writeValueAsString(payloadPath);
    }

    public static byte[] convertStringIntoBytes(String json) {
        return json.getBytes();
    }


}
