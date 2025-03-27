package com.devteria.gateway.filter;

import com.devteria.gateway.models.ApiResponse;
import com.devteria.gateway.models.dto.AuthorizeDTO;
import com.devteria.gateway.models.dto.FunctionDTO;
import com.devteria.gateway.service.IdentityService;
import com.devteria.gateway.utils.PermissionCheckerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final IdentityService identityService;
    private final ObjectMapper objectMapper;
    private final PermissionCheckerUtil permissionCheckerUtil;

    @NonFinal
    private String[] bypassEndpoints = {
            "/identity/auth/.*",
            "/identity/users/registration"
    };

    @Value("${app.api-prefix}")
    @NonFinal
    private String apiPrefix;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication filter....");

        if (isPublicEndpoint(exchange.getRequest()))
            return chain.filter(exchange); // Skip authentication for public endpoints

        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return unauthenticated(exchange.getResponse()); // Return unauthenticated if no token is found

        String token = authHeader.getFirst().replace("Bearer ", "");
        log.error("Token: {}", token);

        // Call introspect and validate the token
        return identityService.introspect(token).flatMap(introspectResponse -> {
            log.error("introspectResponse {}:", introspectResponse);

            if (introspectResponse.getResult().isValid()) {
                log.error("Passed introspectResponse");
                log.error("exchange {} :", exchange);

                String path = exchange.getRequest().getURI().getPath();

                // Check if user has permission for the path
                return permissionCheckerUtil.hasRoleForPath(introspectResponse.getResult().getAuthorizeDTO(), path)
                        .flatMap(hasPermission -> {
                            if (hasPermission) {
                                return chain.filter(exchange); // If user has permission, continue the filter chain
                            } else {
                                return unauthenticated(exchange.getResponse()); // If no permission, return unauthenticated
                            }
                        });
            } else {
                log.error("Authentication failed.");
                return unauthenticated(exchange.getResponse()); // Return unauthenticated if introspect fails
            }
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse())); // Handle any other error
    }

    @Override
    public int getOrder() {
        return -1; // Ensure this filter runs first
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(bypassEndpoints)
                .anyMatch(s -> request.getURI().getPath().matches(apiPrefix + s));
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Unauthenticated")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))); // Write unauthenticated response
    }
}
