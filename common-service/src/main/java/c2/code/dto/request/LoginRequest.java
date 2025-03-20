package c2.code.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String requestId;
    private String userId;
    private String token;
    private String deviceId;
    private long timestamp;
    private String serverId;
    private String clientRequestId;
}
