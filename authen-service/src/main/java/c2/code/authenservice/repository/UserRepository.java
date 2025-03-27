package c2.code.authenservice.repository;

import c2.code.authenservice.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


//@EnableMongoRepositories(basePackages = "c2.code.authenservice.repository")
public interface UserRepository extends MongoRepository<UserEntity, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserEntity findByUsername(String username);

}
