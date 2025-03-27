package c2.code.identityservice.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentLoginResponse {
    private String token; // JWT token
    private AgentResponse agent; // Thông tin người dùng
}



