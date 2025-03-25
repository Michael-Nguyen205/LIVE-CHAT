package com.devteria.gateway.configuration;

import com.devteria.gateway.webclient.IdentityClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Configuration
public class WebClientConfiguration {

    @Bean
    CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }


    @Bean
    WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8084/identity")
                .build();
    }


    @Bean
    IdentityClient identityClient(WebClient webClient){
        //HttpServiceProxyFactory là một công cụ trong Spring WebService cho phép bạn tạo ra các
        // client HTTP dựa trên interface (trong trường hợp này là IdentityClient)
        //dùng để chuyển WebClient vào trong HttpServiceProxyFactory.
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();
        // Sử dụng HttpServiceProxyFactory để tạo client từ interface IdentityClient.
        return httpServiceProxyFactory.createClient(IdentityClient.class);
    }

}
