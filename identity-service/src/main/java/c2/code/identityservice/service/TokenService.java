//package c2.code.authenservice.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//@Service
//@Slf4j
//public class TokenService {
//
//    public static final String DEFAULT_TOKEN = "default_token";
//    @Autowired
//    private JwtService jwtService;
//
//    public boolean validateToken(String userId, String token) {
//        // Đặt token mặc đinh để bypass
//        if (DEFAULT_TOKEN.equals(token)) {
//            return true;
//        }
//
//        return jwtService.verifyToken(userId, token);
//    }
//}
