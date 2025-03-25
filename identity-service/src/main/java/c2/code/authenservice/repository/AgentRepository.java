package c2.code.authenservice.repository;

import c2.code.authenservice.entity.sql.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import reactor.core.publisher.Mono;

public interface AgentRepository extends BaseRepositoryJpa<Agent, String> {



    Mono<Boolean> existsByEmail(String email);


    Mono<Agent> findByUsername(String username);

    Mono<Agent> findByEmail(String email);
    Mono<Agent> findByPhoneNumber(String phoneNumber);

}
