package c2.code.response;

import lombok.Data;

@Data
public class SignUpResponse {

    private long id;
    private String username;
    private String name;
    private String email;
    private String token;
}
