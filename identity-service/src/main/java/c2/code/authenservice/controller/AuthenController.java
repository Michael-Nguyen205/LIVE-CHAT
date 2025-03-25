package c2.code.authenservice.controller;

import c2.code.api.CommonResult;
import c2.code.authenservice.exceptions.AppException;
import c2.code.authenservice.models.request.AgentLoginRequest;
import c2.code.authenservice.models.request.CreateAuthorRequest;
import c2.code.authenservice.models.request.IntrospectRequest;
import c2.code.authenservice.models.request.SignUpSubAgentRequest;
import c2.code.authenservice.models.response.AgentLoginResponse;
import c2.code.authenservice.models.response.IntrospectResponse;
import c2.code.authenservice.service.AuthService;
import c2.code.authenservice.service.IAgentService;
import c2.code.authenservice.service.IAuthenService;
import c2.code.authenservice.service.IAuthorizeService;
import c2.code.authenservice.service.UserService;
import c2.code.dto.request.LoginRequest;
import c2.code.dto.request.SignInRequest;
import c2.code.dto.request.SignUpRequest;
import c2.code.dto.response.LoginResponse;
import c2.code.dto.response.SignInResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@Log4j2
@RequiredArgsConstructor
public class AuthenController {

//    @Autowired
//    private UserService userService;
//    @Autowired
//    private AuthService authService;

//    @PostMapping("/sign-up")
//    public CommonResult signUp(@RequestBody SignUpRequest signUpRequest) {
//        userService.register(signUpRequest);
//        return CommonResult.success();
//    }

    @Qualifier("agentServiceImpl")
    private  final IAgentService agentService;


    @Qualifier("authorizeServiceImpl")
    private  final IAuthorizeService authorizeService;

    @Qualifier("authenServiceImpl")
    private  final IAuthenService  authenService;


    @PostMapping("/sign-up-sub-agent")
    public CommonResult signUpSubAgent(@Valid @RequestBody SignUpSubAgentRequest request) {
        log.error("đã vào register");

        try {
            agentService.createUser(request);
          return   CommonResult.success();
        } catch (AppException e) {
            // Xử lý ngoại lệ và trả về mã lỗi thích hợp
            throw e;
        }
    }


    @PostMapping("/introspect")
    Mono<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {

        log.error("request {} :", request.toString());

        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
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
