package c2.code.authenservice.entity.sql;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "company")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "utc_offset", length = 6)
    private String utcOffset;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "language", length = 50)
    private String language;
}
