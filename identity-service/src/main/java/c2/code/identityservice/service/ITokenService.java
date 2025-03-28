package c2.code.identityservice.service;



import c2.code.identityservice.entity.sql.Token;
import jakarta.validation.constraints.NotBlank;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface ITokenService {
    Mono<Token> addToken(@NotBlank(message = "Phone number is required")UUID agentId, String token, boolean isMobileDevice);
//    Token refreshToken(String refreshToken, Agent agent) throws Exception;
}
