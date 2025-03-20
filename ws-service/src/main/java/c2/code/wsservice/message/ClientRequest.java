package c2.code.wsservice.message;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class ClientRequest {

    private String mid;
    private String userId;
    private String deviceId;
    private long timestamp;
    private JsonObject content;
}
