package c2.code.authenservice.controller;

import c2.code.api.CommonResult;
import c2.code.authenservice.exceptions.AppException;
import c2.code.authenservice.models.request.CreateDepartmentRequest;
import c2.code.authenservice.service.AuthService;
import c2.code.authenservice.service.IDepartmentService;
import c2.code.authenservice.service.UserService;
import c2.code.dto.request.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deparment")
@Log4j2
@RequiredArgsConstructor
public class DeparmentController {


    @Qualifier("deparmentServiceImpl")
    private  final IDepartmentService deparmentService;
    @PostMapping("")
    public CommonResult createDeparment(@Valid @RequestBody CreateDepartmentRequest request) {
        log.error("đã vào register");

        try {
            agentService.createUser(request);
          return   CommonResult.success();
        } catch (AppException e) {
            // Xử lý ngoại lệ và trả về mã lỗi thích hợp
            throw e;
        }
    }




//    @PostMapping("/sign-up-sub-agent")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<AuthorizeResponse> createAuthor(@RequestBody @Valid CreatePermissionRequest request) {
//        log.error("da vao day");
//        return permissionService.createAuthor(request)
////                .onErrorResume(IllegalArgumentException.class,e -> {
////                    log.error("đã vao IllegalArgumentException");
////                    throw new AppException(ErrorCode.DATABASE_SAVE_ERROR, "Lỗi khi permissionRepository.findById" );
////                })
//                .onErrorResume(AppException.class, e -> {
//                    throw e;
//                });
//    }



//    @PostMapping("/sign-in")
//    public CommonResult<SignInResponse> singIn(@RequestBody SignInRequest signInRequest) {
//        return CommonResult.success(authService.authenticate(signInRequest.getUsername(), signInRequest.getPassword()));
//    }


    // Sử dụng api này để login websocket
//    @PostMapping("/login")
//    public CommonResult<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
//        return CommonResult.success(authService.login(loginRequest));
//    }
}
