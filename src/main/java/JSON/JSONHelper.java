package JSON;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONHelper {

    @SneakyThrows
    public JSONObject convertResponseBodyIntoJSON(HttpEntity httpEntity) {
        InputStream inputStream = httpEntity.getContent();
        StringBuilder responseStrBuilder = new StringBuilder();
        try {
            BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bR.readLine()) != null) {
                responseStrBuilder.append(line);
            }
        } finally {
            inputStream.close();
        }
        return new JSONObject(responseStrBuilder.toString());
    }

}
