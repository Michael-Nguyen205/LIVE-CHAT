package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "action")
public class Action extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "department_id", nullable = false)
    private UUID departmentId;  // Foreign key to Department (UUID)

    @Column(name = "function_id", nullable = false)
    private UUID functionId;  // Foreign key to Function (UUID)

    @Column(name = "is_for_department")
    private boolean isForDepartment;
}
