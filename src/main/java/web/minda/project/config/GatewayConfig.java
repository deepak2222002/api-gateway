package web.minda.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

import java.util.List;

@Configuration
public class GatewayConfig {

    @Value("${gateway.auth.url}")
    private String authServiceUrl;

    @Value("${gateway.user.url}")
    private String userServiceUrl;

    @Value("${gateway.userActivate.url}")
    private String userActivateServiceUrl;


    // ================= CORS =================

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:3000"
        ));

        config.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        config.setAllowedHeaders(List.of("*"));

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }


    // ================= AUTH SERVICE =================

    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {

        return route("auth-service")
                .route(
                        path("/auth/**")
                                .or(path("/projectLoginpage/**"))
                                .or(path("/projectEmployeeDashboard/**")),
                        http()
                )
                .before(uri(authServiceUrl))
                .build();
    }


    // ================= USER SERVICE =================

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {

        return route("user-service")
                .route(
                        path("/user/**"),
                        http()
                )
                .before(uri(userServiceUrl))
                .build();
    }


    // ================= USER ACTIVATION SERVICE =================

    @Bean
    public RouterFunction<ServerResponse> userActivateServiceRoute() {

        return route("user-activation-service")
                .route(
                        path("/activate/**"),
                        http()
                )
                .before(uri(userActivateServiceUrl))
                .build();
    }
}