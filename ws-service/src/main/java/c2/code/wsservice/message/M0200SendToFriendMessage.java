package c2.code.wsservice.message;

import lombok.Data;

@Data
public class M0200SendToFriendMessage extends BaseMessage {

    long messageId;
    String id;
    String destId;
    String message;

}
