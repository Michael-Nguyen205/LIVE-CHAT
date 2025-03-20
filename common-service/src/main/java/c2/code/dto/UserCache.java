package c2.code.dto;

import lombok.Data;

@Data
public class UserCache {

    private String id;
    private String name;
    private long lastLogin;
    private String avatarUrl;
    private String thumbAvatarUrl;
}
