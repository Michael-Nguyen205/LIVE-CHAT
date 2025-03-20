package c2.code.dto.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String session;
    private String name;
    private String avatarUrl;
    private String thumbAvatarUrl;
}
