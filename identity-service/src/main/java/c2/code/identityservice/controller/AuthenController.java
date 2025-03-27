package c2.code.identityservice.controller;

//import c2.code.api.CommonResult;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.models.request.AgentLoginRequest;
import c2.code.identityservice.models.request.IntrospectRequest;
import c2.code.identityservice.models.request.SignUpSubAgentRequest;
import c2.code.identityservice.models.response.AgentLoginResponse;
import c2.code.identityservice.models.response.IntrospectResponse;
import c2.code.identityservice.service.IAgentService;
import c2.code.identityservice.service.IAuthenService;
import c2.code.identityservice.service.IAuthorizeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")

@Log4j2
@RequiredArgsConstructor
public class AuthenController {



    @Qualifier("agentServiceImpl")
    private  final IAgentService agentService;


    @Qualifier("authorizeServiceImpl")
    private  final IAuthorizeService authorizeService;

    @Qualifier("authenServiceImpl")
    private  final IAuthenService  authenService;


//    @PostMapping("/sign-up-sub-agent")
//    public CommonResult signUpSubAgent(@Valid @RequestBody SignUpSubAgentRequest request) {
//        log.error("đã vào register");
//
//        try {
//            agentService.createUser(request);
//          return   CommonResult.success();
//        } catch (AppException e) {
//            // Xử lý ngoại lệ và trả về mã lỗi thích hợp
//            throw e;
//        }
//    }


    @PostMapping("/introspect")
    Mono<ResponseEntity<IntrospectResponse>> authenticate(@RequestBody IntrospectRequest request) {

        log.error("request {} :", request.toString());

        return  authenService.introSpect(request).map(
                        result -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result))
                .onErrorResume(AppException.class, e -> {
                    throw e;
                });
    }




    @PostMapping("/login")
    public  Mono<ResponseEntity<AgentLoginResponse>> login(@Valid @RequestBody AgentLoginRequest userLoginRequest ,
                                                           ServerHttpRequest request){
        return authenService.login(userLoginRequest.getEmail(),userLoginRequest.getPassword(),request).map(
                        result -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result))
                .onErrorResume(AppException.class, e -> {
                    throw e;
                });
    }

}
