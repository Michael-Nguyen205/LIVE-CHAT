package c2.code.identityservice.repository;

import c2.code.identityservice.entity.sql.Agent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AgentRepository extends BaseRepositoryReactive<Agent, UUID> {


    Mono<Agent> findByEmail(String email);
    Mono<Boolean> existsByEmail(String email);



}
