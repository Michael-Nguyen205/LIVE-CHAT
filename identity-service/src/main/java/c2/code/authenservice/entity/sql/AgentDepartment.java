package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.util.UUID;

@Data
@Entity
@Table(name = "agent_department")
public class AgentDepartment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    @Column(name = "agent_id", nullable = false)
    private UUID agentId;  // Foreign key to Agent (UUID)

    @Column(name = "department_id", nullable = false)
    private Integer permissionId;  // Foreign key to Permission (UUID)
}
