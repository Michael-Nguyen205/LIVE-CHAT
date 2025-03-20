package c2.code.wsservice.dto;

import lombok.Data;

@Data
public class M0200ChatData {

    private long messageId;
    private String destId;
    private String message;
    private String mid;
    private long serverTime;
    private String senderId;
    private String groupId;
}
