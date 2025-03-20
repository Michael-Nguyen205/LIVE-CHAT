package c2.code.authenservice.service;

import c2.code.api.ResultCode;
import c2.code.authenservice.dao.UserRedisDao;
import c2.code.authenservice.entity.UserEntity;
import c2.code.authenservice.repository.UserRepository;
import c2.code.dto.UserCache;
import c2.code.dto.request.LoginRequest;
import c2.code.dto.response.LoginResponse;
import c2.code.dto.response.SignInResponse;
import c2.code.exception.ApiException;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    private Gson gson = new Gson();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRedisDao userRedisDao;
    @Autowired
    private TokenService tokenService;

    public SignInResponse authenticate(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            log.error("User not found {}", username);
            throw ApiException.failed(ResultCode.INVALID_USERNAME_PASSWORD);
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            log.error("Invalid password {}", username);
            throw ApiException.failed(ResultCode.INVALID_USERNAME_PASSWORD);
        }

        log.info("User login successful {}. Start generate jwt token", username);
        String jwtToken = jwtService.generateJwtToken(user.getId());

        SignInResponse response = new SignInResponse();
        BeanUtils.copyProperties(user, response);
        response.setToken(jwtToken);
        return response;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Received login request {}", gson.toJson(loginRequest));

        // Kiểm tra user tồn tại
        UserCache user = userRedisDao.getUser(loginRequest.getUserId());
        if (Objects.isNull(user)) {
            log.error("User not found {}", loginRequest.getUserId());
            throw ApiException.failed(ResultCode.USER_NOT_FOUND);
        }

        // Kiểm tra token, mặc định token là default_token, hoặc jwt token sau khi login thành công
        boolean isValidToken = tokenService.validateToken(loginRequest.getUserId(), loginRequest.getToken());
        if (!isValidToken) {
            log.error("Invalid token {}", loginRequest.getToken());
            throw ApiException.failed(ResultCode.INVALID_TOKEN);
        }

        log.info("User login successful {}. Start generate session", loginRequest.getUserId());
        // Lưu lại lần cuối đăng nhập của user, user khác có thể tìm kiếm thông tin hiển thị kiểu ( userA đã online vào 1h trước )
        user.setLastLogin(System.currentTimeMillis());
        userRedisDao.putUser(user.getId(), user);

        // lưu thông tin session chứa serverId client đã kết nối đến
        String session = loginRequest.getServerId() + "|" + UUID.randomUUID().toString();
        userRedisDao.putUserSession(loginRequest.getUserId(), session);

        LoginResponse response = new LoginResponse();
        response.setSession(session);
        response.setName(user.getName());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setThumbAvatarUrl(user.getThumbAvatarUrl());
        return response;
    }

}
