package c2.code.wsservice.service.api;

import c2.code.api.CommonResult;
import c2.code.request.CheckSendMessageRequest;
import c2.code.response.CheckSendMessageResponse;
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
public class UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${api.url.auth-service:http://localhost:9001/users/}")
    private String url;

    public CommonResult<CheckSendMessageResponse> canSendMessage(CheckSendMessageRequest loginRequest) {
        String userUrl = url + "can-send-message";
        HttpEntity<CheckSendMessageRequest> entity = new HttpEntity<>(loginRequest);
        ResponseEntity<CommonResult<CheckSendMessageResponse>> response = restTemplate.exchange(
                userUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
}
