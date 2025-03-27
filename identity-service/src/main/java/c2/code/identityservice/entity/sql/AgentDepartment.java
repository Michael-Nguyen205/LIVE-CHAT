package c2.code.identityservice.entity.sql;//package c2.code.identityservice.entity.sql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "agent_department")
public class AgentDepartment extends BaseEntity {

    @Id
    private Integer id;


    private UUID agentId;  // Foreign key to Agent (UUID)

    private Integer permissionId;  // Foreign key to Permission (UUID)
}
