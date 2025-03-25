package c2.code.authenservice.service;

import c2.code.authenservice.models.request.CreateDepartmentRequest;
import org.springframework.stereotype.Component;



@Component
public interface IDepartmentService {
//    Users createUser(UserRegisterRequest userRegisterRequest) throws Exception;

   Void createDepartment(CreateDepartmentRequest request);







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