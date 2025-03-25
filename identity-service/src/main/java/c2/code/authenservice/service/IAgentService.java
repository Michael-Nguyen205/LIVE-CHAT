package c2.code.authenservice.service;

import c2.code.authenservice.models.request.SignUpSubAgentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component
public interface IAgentService {
//    Users createUser(UserRegisterRequest userRegisterRequest) throws Exception;

   Void createUser(SignUpSubAgentRequest request);

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