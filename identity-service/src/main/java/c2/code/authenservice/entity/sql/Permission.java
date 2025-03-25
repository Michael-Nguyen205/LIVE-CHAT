package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "is_custom", nullable = false)
    private boolean isCustom;

    @Column(name = "created_by")
    private UUID createdBy;  // Foreign key to Agent (UUID)
}
