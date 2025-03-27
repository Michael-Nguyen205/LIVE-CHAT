package c2.code.identityservice.entity.sql;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "agent")
public class Agent extends BaseEntity {

    @Id
    private UUID id;

    private UUID companyId;  // Foreign key to Company (UUID)

    private String fullname;

    private String alias;

    private String email;

    private String phoneNumber;

    private String password;

    private boolean isMain;

    private boolean isActive;

    private UUID permissionId;



}
