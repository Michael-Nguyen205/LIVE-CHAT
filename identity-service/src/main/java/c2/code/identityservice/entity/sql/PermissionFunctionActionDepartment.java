package c2.code.identityservice.entity.sql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "permission_function_action_department")
public class PermissionFunctionActionDepartment extends BaseEntity {

    @Id
    private UUID id;

    private UUID function;  // Foreign key to Function (UUID)

    private UUID actionId;  // Foreign key to Action (UUID)

    private UUID permissionId;  // Foreign key to Permission (UUID)

    private UUID departmentId;  // Foreign key to Department (UUID)
}
