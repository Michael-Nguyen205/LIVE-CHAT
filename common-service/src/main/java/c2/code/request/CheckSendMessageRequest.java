package c2.code.request;

import lombok.Data;

@Data
public class CheckSendMessageRequest {

    private String requestId;
    private String userId;
    private String destId;
}
