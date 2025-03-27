package c2.code.identityservice.entity.sql;


import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @Column( "created_at")
    private LocalDateTime createdAt;

    @Column( "updated_at")
    private LocalDateTime updatedAt;
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
