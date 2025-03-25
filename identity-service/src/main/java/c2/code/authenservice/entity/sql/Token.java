package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    @Column(name = "is_mobile")
    private boolean isMobile;

    @Column(name = "refresh_expiration_date")
    private String refreshExpirationDate;

    @Column(name = "refresh_token", nullable = false, length = 512)
    private String refreshToken;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "token", nullable = false, length = 512)
    private String token;

    @Column(name = "agent_id", nullable = false)
    private UUID agentId;  // Foreign key to Agent (UUID)

    @Column(name = "version")
    private String version;
}
