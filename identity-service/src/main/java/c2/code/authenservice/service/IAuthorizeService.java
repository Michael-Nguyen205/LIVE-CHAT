package c2.code.authenservice.service;


import c2.code.authenservice.models.dto.AuthorizeDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface IAuthorizeService {


    Mono<AuthorizeDTO> getAuthor(String permissionId);

}
