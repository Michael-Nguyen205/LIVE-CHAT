package c2.code.authenservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "user")
@Data
public class UserEntity {

    @Id
    private long id;

    private String username;

    private String name;

    private String password;

    private String email;

    private Date createdAt;

}
