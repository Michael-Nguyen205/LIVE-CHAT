package c2.code.identityservice.entity.sql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "permission")
public class Permission extends BaseEntity {

    @Id
    private UUID id;

    private String name;

    private boolean isCustom;

    private UUID createdBy;  // Foreign key to Agent (UUID)
}
