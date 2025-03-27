package c2.code.identityservice.entity.sql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "action")
public class Action extends BaseEntity {

    @Id
    private UUID id;

    private String name;

    private UUID departmentId;  // Foreign key to Department (UUID)

    private UUID functionId;  // Foreign key to Function (UUID)

    private boolean isForDepartment;
}
