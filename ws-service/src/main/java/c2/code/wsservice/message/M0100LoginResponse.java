package c2.code.wsservice.message;

import lombok.Data;

@Data
public class M0100LoginResponse extends BaseResponse {

    String avatarUrl;
    String thumbAvatarUrl;
    String name;
    String session;
}
