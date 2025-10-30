////package com.cts.api_gateway.config;
////
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.Jwts;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.cloud.gateway.filter.GatewayFilterChain;
////import org.springframework.cloud.gateway.filter.GlobalFilter;
////import org.springframework.core.Ordered;
////import org.springframework.http.HttpStatus;
////import org.springframework.stereotype.Component;
////import org.springframework.web.server.ServerWebExchange;
////import reactor.core.publisher.Mono;
////
////@Component
////public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
////
////    @Value("${jwt.secret}")
////    private String secretKey;
////
////    @Override
////    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
////        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
////
////        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
////            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
////            return exchange.getResponse().setComplete();
////        }
////
////        String token = authHeader.substring(7);
////        try {
////            Claims claims = Jwts.parser()
////                    .setSigningKey(secretKey.getBytes())
////                    .parseClaimsJws(token)
////                    .getBody();
////            // Optional: pass claims downstream via headers
////        } catch (Exception e) {
////            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
////            return exchange.getResponse().setComplete();
////        }
////
////        return chain.filter(exchange);
////    }
////
////    @Override
////    public int getOrder() {
////        return -1; // High precedence
////    }
////}
////
//
////package com.cts.api_gateway.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.reactive.CorsWebFilter;
////import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
////
////@Configuration
////public class GatewayConfig {
////
////    @Bean
////    public CorsWebFilter corsWebFilter() {
////        CorsConfiguration corsConfig = new CorsConfiguration();
////        corsConfig.setAllowCredentials(true);
////        corsConfig.addAllowedOriginPattern("*"); // Allow all origins
////        corsConfig.addAllowedHeader("*"); // Allow all headers
////        corsConfig.addAllowedMethod("*"); // Allow all HTTP methods
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", corsConfig);
////
////        return new CorsWebFilter(source);
////    }
////}
//
//package com.cts.api_gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // User Service Routes
//                .route("user-service-direct", r -> r.path("/user/**")
//                        .uri("http://localhost:8088"))
//                .route("user-service-api", r -> r.path("/api/user/**")
//                        .filters(f -> f.rewritePath("/api/user/(?<path>.*)", "/user/${path}"))
//                        .uri("http://localhost:8088"))
//
//                // Driver Service Routes
//                .route("driver-service-direct", r -> r.path("/driver/**")
//                        .uri("http://localhost:8087"))
//                .route("driver-service-api", r -> r.path("/api/driver/**")
//                        .filters(f -> f.rewritePath("/api/driver/(?<path>.*)", "/driver/${path}"))
//                        .uri("http://localhost:8087"))
//
//                // Booking Service Routes
//                .route("booking-service-direct", r -> r.path("/ride/**")
//                        .uri("http://localhost:8089"))
//                .route("booking-service-api", r -> r.path("/api/bookings/**")
//                        .filters(f -> f.rewritePath("/api/bookings/(?<path>.*)", "/ride/${path}"))
//                        .uri("http://localhost:8089"))
//
//                .build();
//    }
//}
//
//
