//package c2.code.identityservice.utils;//package spring.boot.authenauthor.utils;
//
//import c2.code.identityservice.enums.ErrorCodeEnum;
//import c2.code.identityservice.exceptions.AppException;
//import c2.code.identityservice.repository.PermissionFuntionActionDepartmentRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.dao.DataAccessException;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//
//@RequiredArgsConstructor
//@Log4j2
//@Component
//public class AuthorUtils {
//    private final PermissionFuntionActionDepartmentRepository permissionFuntionActionDepartmentRepository;
//
//
//
//        public Mono<Boolean> hasAuthor(String function, String action, Boolean forAgent) {
//            // Thiết lập giá trị mặc định cho model và rawId nếu cần thiết
//            return ReactiveSecurityContextHolder.getContext()
//                    .map(SecurityContext::getAuthentication)
//                    .flatMap(authentication -> {
//                        if (authentication == null) {
//                            log.error("Authentication is null");
//                            return Mono.error(new AppException(ErrorCodeEnum.UNAUTHORIZED, "Authentication không hợp lệ"));
//                        }
//                        log.error("authenticationnnnnn: {}", authentication);
//                        Object principal = authentication.getPrincipal();
//                        log.error("principal: {}", principal);
//                        if (!(principal instanceof UserDetails userDetails)) {
//                            log.error("Principal is not an instance of UserDetails");
//                            return Mono.error(new AppException(ErrorCodeEnum.UNAUTHORIZED, "Principal không hợp lệ"));
//                        }
//                        final String username = userDetails.getUsername();
//                        if (username == null) {
//                            log.error("Username is null in UserDetails");
//                            return Mono.error(new AppException(ErrorCodeEnum.UNAUTHORIZED, "Username không hợp lệ"));
//                        }
//
//
//
//
//
//                        try {
//                            log.error("tryyyyyyyyyyyyyy");
//
////
//
//                            return permissionFuntionActionDepartmentRepository.hasAuthor(username, actionId, functionId,departmentId)
//                                    .onErrorResume( e -> {
//                                        log.error("tryyyyyyyyyyyy looxi");
//                                        try {
//                                            throw e;
//                                        } catch (Throwable ex) {
//                                            throw new AppException(ErrorCodeEnum.UNAUTHENTICATED,"khong co quyen");
//                                        }
//                                    })
//                                    .map(result -> result > 0).doOnSuccess(count -> log.info("Count for model: {}", count)); // Ghi lại số lượng
//
//                        } catch (DataAccessException e) {
//                            // Xử lý lỗi cơ sở dữ liệu
//                            log.error("Database error while checking permissions: ", e);
//                            return Mono.error(new RuntimeException("An error occurred while checking permissions.", e));
//                        } catch (Exception e) {
//                            // Xử lý lỗi chung
//                            log.error("Unexpected error while checking permissions: ", e);
//                            return Mono.error(new RuntimeException("An unexpected error occurred while checking permissions.", e));
//                        }
//                    })
//                    .onErrorResume(e -> {
//                        log.error("Error in hasAuthor: ", e);
//                        return Mono.just(false); // hoặc xử lý khác tùy vào yêu cầu
//                    });
//        }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
