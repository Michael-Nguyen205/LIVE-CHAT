package c2.code.identityservice.service;


import c2.code.identityservice.models.dto.AuthorizeDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface IAuthorizeService {


    Mono<AuthorizeDTO> getAuthor(String permissionId);

}
