package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "agent_permission")
public class AgentPermission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "agent_id", nullable = false)
    private UUID agentId;  // Foreign key to Agent (UUID)

    @Column(name = "permission_id", nullable = false)
    private UUID permissionId;  // Foreign key to Permission (UUID)
}
