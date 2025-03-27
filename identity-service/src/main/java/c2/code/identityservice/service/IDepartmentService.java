package c2.code.identityservice.service;

import c2.code.identityservice.models.request.CreateDepartmentRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public interface IDepartmentService {
//    Users createUser(UserRegisterRequest userRegisterRequest) throws Exception;

   Mono<Void> createDepartment(CreateDepartmentRequest request);







//    UserLoginResponse login(String email, String password , HttpServletRequest request ) ;
//
//
//     Users getUser(String userId);
//    void deleteUser(String userId);
//
//
//
//    void approveUser(Boolean statusActive,String userId );

//    String changePass( String phoneNumber , String password) throws Exception;
//    Users getUserDetailsFro
}