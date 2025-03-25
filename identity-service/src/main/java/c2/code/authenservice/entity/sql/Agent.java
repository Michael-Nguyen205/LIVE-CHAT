package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "agent")
public class Agent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;  // Foreign key to Company (UUID)

    @Column(name = "fullname", nullable = false, length = 50)
    private String fullname;

    @Column(name = "alias", nullable = false, length = 50)
    private String alias;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "is_main", nullable = false)
    private boolean isMain;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "permission_id", nullable = false)
    private UUID permissionId;


    public Agent() {

    }
}
