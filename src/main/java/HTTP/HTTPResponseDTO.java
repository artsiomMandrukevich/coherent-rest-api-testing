package HTTP;

import org.json.JSONObject;

public class HTTPResponseDTO {

    public String status;
    public JSONObject body;

    public HTTPResponseDTO() {
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public JSONObject getBody() {
        return this.body;
    }

    public String getStatus() {
        return this.status;
    }

}
