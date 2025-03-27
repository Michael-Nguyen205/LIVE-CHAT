package c2.code.identityservice.entity.sql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "company")
public class Company extends BaseEntity {

    @Id
    private UUID id;

    private String companyName;

    private String email;

    private String website;

    private String logoUrl;

    private String timezone;

    private String utcOffset;

    private String country;

    private String language;
}
