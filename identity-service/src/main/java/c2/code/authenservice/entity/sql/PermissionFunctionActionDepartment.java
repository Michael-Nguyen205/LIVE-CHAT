package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "permission_function_action_department")
public class PermissionFunctionActionDepartment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "function", nullable = false)
    private UUID function;  // Foreign key to Function (UUID)

    @Column(name = "action_id", nullable = false)
    private UUID actionId;  // Foreign key to Action (UUID)

    @Column(name = "permission_id", nullable = false)
    private UUID permissionId;  // Foreign key to Permission (UUID)

    @Column(name = "department_id", nullable = false)
    private UUID departmentId;  // Foreign key to Department (UUID)
}
