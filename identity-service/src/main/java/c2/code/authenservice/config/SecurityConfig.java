package c2.code.authenservice.config;

import c2.code.authenservice.enums.ErrorCodeEnum;
import c2.code.authenservice.exceptions.AppException;
import c2.code.authenservice.models.auth.CustomUserDetails;
import c2.code.authenservice.repository.AgentRepository;
import c2.code.authenservice.service.IAuthorizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;



@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

    private final AgentRepository agentRepository;


    @Qualifier("authorizeServiceImpl")
    private final IAuthorizeService authorizeService;


    @Bean
    @Primary
    public ReactiveUserDetailsService userDetailsService() {
        return email -> agentRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new AppException(ErrorCodeEnum.USER_NOT_EXISTED,"email không tồn tại")))
                // Nếu authorities rỗng sẽ có thể sảy ra lỗi
                .flatMap(user -> authorizeService.getAuthor(user.getEmail())
                        .switchIfEmpty(Mono.error(new AppException(ErrorCodeEnum.USER_NOT_EXISTED,"tài khoản nay không có tole")))
                        .map(authorities -> (UserDetails) new CustomUserDetails(user, null,authorities)));// Sử dụng CustomUserDetails
    }







    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }


}
