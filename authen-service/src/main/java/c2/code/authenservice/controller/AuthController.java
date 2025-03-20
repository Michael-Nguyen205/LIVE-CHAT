package c2.code.authenservice.controller;

import c2.code.api.CommonResult;
import c2.code.authenservice.service.AuthService;
import c2.code.authenservice.service.UserService;
import c2.code.dto.request.LoginRequest;
import c2.code.dto.request.SignInRequest;
import c2.code.dto.request.SignUpRequest;
import c2.code.dto.response.LoginResponse;
import c2.code.dto.response.SignInResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public CommonResult signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.register(signUpRequest);
        return CommonResult.success();
    }

    @PostMapping("/sign-in")
    public CommonResult<SignInResponse> singIn(@RequestBody SignInRequest signInRequest) {
        return CommonResult.success(authService.authenticate(signInRequest.getUsername(), signInRequest.getPassword()));
    }


    // Sử dụng api này để login websocket
    @PostMapping("/login")
    public CommonResult<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return CommonResult.success(authService.login(loginRequest));
    }
}
