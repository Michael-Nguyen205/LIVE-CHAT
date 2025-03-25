//package com.devteria.gateway.component;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
//
//    public AuthFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            // Logic kiểm tra xác thực ở đây
//            return chain.filter(exchange);
//        };
//    }
//
//    public static class Config {
//        // Thêm các thuộc tính cấu hình nếu cần
//    }
//}
