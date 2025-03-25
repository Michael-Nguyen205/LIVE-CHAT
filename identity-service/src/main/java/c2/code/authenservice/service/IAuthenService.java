package c2.code.authenservice.service;


import c2.code.authenservice.models.dto.AuthorizeDTO;
import c2.code.authenservice.models.request.IntrospectRequest;
import c2.code.authenservice.models.response.AgentLoginResponse;
import c2.code.authenservice.models.response.IntrospectResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface IAuthenService {


    Mono<IntrospectResponse> introSpect(IntrospectRequest request);

    Mono<AgentLoginResponse> login(String email, String password , ServerHttpRequest request ) ;


}
