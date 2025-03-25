package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "department")
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;  // Foreign key to Company (UUID)

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    public Department() {

    }
}
