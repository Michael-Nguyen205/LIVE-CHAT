package c2.code.identityservice.models.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgentLoginRequest {

    @NotBlank(message = "Email number is required")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

}