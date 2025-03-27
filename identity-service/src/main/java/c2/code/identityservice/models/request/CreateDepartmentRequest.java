package c2.code.identityservice.models.request;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class CreateDepartmentRequest {
    private String name;
    private UUID companyId;
    private Set<UUID> agentIds;
    private String alias;
}
