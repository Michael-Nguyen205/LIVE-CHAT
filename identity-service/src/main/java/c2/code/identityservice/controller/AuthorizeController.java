package c2.code.identityservice.controller;

import c2.code.identityservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/vi/authorize")
@RequiredArgsConstructor
public class AuthorizeController {


//    private final IAuthorizeService permissionService;
    private final PermissionRepository permissionRepository;





//    @PostMapping ("")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<CommonResult> createAuthor(@RequestBody @Valid CreateAuthorRequest request) {
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
//


//    @PostMapping ("")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<CommonResult> createAuthor(@RequestBody @Valid CreateAuthorRequest request) {
//        log.error("da vao day");
//        return  CommonResult.success()
//                .onErrorResume(AppException.class, e -> {
//                    throw e;
//                });
//    }


}











