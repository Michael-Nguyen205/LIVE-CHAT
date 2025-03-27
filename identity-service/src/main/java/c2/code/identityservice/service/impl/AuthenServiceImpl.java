package c2.code.identityservice.service.impl;

import c2.code.identityservice.entity.sql.Agent;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.models.auth.CustomUserDetails;
import c2.code.identityservice.models.request.IntrospectRequest;
import c2.code.identityservice.models.response.AgentLoginResponse;
import c2.code.identityservice.models.response.AgentResponse;
import c2.code.identityservice.models.response.IntrospectResponse;
import c2.code.identityservice.repository.AgentRepository;
import c2.code.identityservice.service.IAuthenService;
import c2.code.identityservice.service.ITokenService;
import c2.code.identityservice.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@Service("authenServiceImpl")  // Đặt tên cho bean
public class AuthenServiceImpl implements IAuthenService {

    private  final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReactiveAuthenticationManager authenticationManager;
    private  final ITokenService tokenService;
    private final JwtTokenUtils jwtTokenUtil;



    @Override
    public Mono<IntrospectResponse> introSpect(IntrospectRequest request) {
        String jwt = request.getToken();

        // Gọi verifyToken và sau đó kiểm tra token hợp lệ
        return jwtTokenUtil.verifyToken(jwt)
                .flatMap(claimsJws -> {
                    // Token hợp lệ, tiếp tục gọi extractAuthorize
                    return jwtTokenUtil.extractAuthorize(jwt)
                            .map(authorize -> {
                                // Tạo IntrospectResponse khi cả hai phương thức đã thành công
                                return IntrospectResponse.builder()
                                        .valid(true) // Token hợp lệ
                                        .authorizeDTO(authorize) // Thêm thông tin từ AuthorizeDTO
                                        .build();
                            });
                })
                .onErrorResume(e -> {
                    // Nếu có bất kỳ lỗi nào trong verifyToken hoặc extractAuthorize, xử lý lỗi và trả về phản hồi không hợp lệ
                    return Mono.just(IntrospectResponse.builder()
                            .valid(false) // Token không hợp lệ
                            .build());
                });
    }





    @Override
    public Mono<AgentLoginResponse> login(String email, String password, ServerHttpRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(authenticationToken)
                .onErrorResume(AppException.class,ex -> {
                    ex.printStackTrace();
                    log.error("Login error: {}", ex.getMessage());
                    throw ex;
//                                return Mono.error(new AppException(ErrorCode.DATABASE_SAVE_ERROR,"loi tao token")); // Trả về lỗi
                })
                .flatMap(authentication -> {
                    log.error("user : {}", authentication.getAuthorities());
                    log.error("user : {}", authentication.getPrincipal().toString());
                    log.error("user : {}", authentication);

                    List<String> permisstionList = authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList(); // Thu thập vào List<String>

                    // Chuyển đổi principal về CustomUserDetails
                    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                    Agent agent = customUserDetails.getAgent();

                    // Tạo token JWT
                    return jwtTokenUtil.generateToken(authentication)
                            .onErrorResume(ex -> {
                                ex.printStackTrace();
                                log.error("Login error: {}", ex.getMessage());
                                return Mono.error(ex);
//                                return Mono.error(new AppException(ErrorCode.DATABASE_SAVE_ERROR,"loi tao token")); // Trả về lỗi
                            })
                            .flatMap(token -> {
                                String userAgent = request.getHeaders().getFirst(HttpHeaders.USER_AGENT);
                                log.error("headers: {}", userAgent);
                                // Kiểm tra nếu là thiết bị di động
                                return isMobileDevice(userAgent)
                                        .flatMap(checkMobile -> {
                                            // Gọi dịch vụ để thêm token
                                            return tokenService.addToken(agent.getId(), token, checkMobile)
                                                    .thenReturn(
                                                            AgentLoginResponse.builder()
                                                                    .agent(AgentResponse.toUserResponse(agent))
                                                                    .token(token)
                                                                    .build() // Đảm bảo bạn build đối tượng UserLoginResponse
                                                    ).onErrorResume( AppException.class, e->{
                                                        e.printStackTrace();
                                                        return Mono.error(e);

                                                    });
                                        });
                            });
                })   ;
//                .as(operator::transactional);

    }

    private Mono<Boolean> isMobileDevice(String userAgent) {
        return Mono.just(userAgent != null && userAgent.toLowerCase().contains("mobile")); // Kiểm tra User-Agent
    }






}