package c2.code.identityservice.entity.sql;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder
@Table(name = "tokens")
public class Token extends BaseEntity {

    @Id
    private UUID id;

        private LocalDateTime expirationDate;

    private boolean expired;

    private boolean isMobile;

    private LocalDateTime refreshExpirationDate;

    private String refreshToken;

    private boolean revoked;

    private String token;

    private UUID agentId;  // Foreign key to Agent (UUID)


    private String version;

//    public Token() {
//
//    }
}
