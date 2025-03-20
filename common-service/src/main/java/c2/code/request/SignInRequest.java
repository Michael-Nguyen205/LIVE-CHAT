package c2.code.request;


import lombok.Data;

@Data
public class SignInRequest {

    private String username;
    private String password;
}
