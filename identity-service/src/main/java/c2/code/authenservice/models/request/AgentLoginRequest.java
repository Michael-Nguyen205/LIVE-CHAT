package c2.code.authenservice.models.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
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