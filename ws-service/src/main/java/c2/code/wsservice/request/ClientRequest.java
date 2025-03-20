package c2.code.wsservice.request;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class ClientRequest {

    private String mid;
    private String userId;
    private String deviceId;
    private String timestamp;
    private String ip;
    private String browser;
    private String chromeVersion;
    private String utc;
    private String country;
    private String language;
    private String url;
    private Object content; // Dùng JsonNode để linh hoạt với JSON phức tạp

}
