package c2.code.dto.response;

import lombok.Data;

@Data
public class SignInResponse {

    private long id;
    private String username;
    private String name;
    private String email;
    private String token;
}
