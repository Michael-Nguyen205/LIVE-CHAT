package c2.code.identityservice.models.request;

import lombok.Data;

import java.util.UUID;

@Data
public class SignUpSubAgentRequest {
    private String fullname;
    private String email;
    private String alias;
    private UUID permissionId;
    private String name;
    private UUID companyId;
}
