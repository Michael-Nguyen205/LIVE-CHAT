package c2.code.identityservice.entity.sql;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "department")
public class Department extends BaseEntity {

    @Id
    private Integer id;

    private UUID companyId;  // Foreign key to Company (UUID)

    private String name;

    private String email;

//    public Department() {
//
//    }
}
