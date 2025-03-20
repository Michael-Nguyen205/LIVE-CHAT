package c2.code.wsservice.service.api;

import c2.code.api.CommonResult;
import c2.code.request.LoginRequest;
import c2.code.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${api.url.auth-service:http://localhost:9000/auth/}")
    private String url;

    public CommonResult<LoginResponse> login(LoginRequest loginRequest) {
        String loginUrl = url + "login";
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest);
        ResponseEntity<CommonResult<LoginResponse>> response = restTemplate.exchange(
                loginUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
}
