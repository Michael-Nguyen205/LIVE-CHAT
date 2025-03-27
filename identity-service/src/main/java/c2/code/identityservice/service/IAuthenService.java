package c2.code.identityservice.service;


import c2.code.identityservice.models.request.IntrospectRequest;
import c2.code.identityservice.models.response.AgentLoginResponse;
import c2.code.identityservice.models.response.IntrospectResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface IAuthenService {


    Mono<IntrospectResponse> introSpect(IntrospectRequest request);

    Mono<AgentLoginResponse> login(String email, String password , ServerHttpRequest request ) ;


}
