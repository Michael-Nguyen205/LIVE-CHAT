package c2.code.identityservice.repository;




import c2.code.identityservice.entity.sql.Token;
import reactor.core.publisher.Flux;

import java.util.UUID;


public interface TokenRepository extends BaseRepositoryReactive<Token, Integer> {

   Flux<Token> findByAgentId(UUID agentId);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}

