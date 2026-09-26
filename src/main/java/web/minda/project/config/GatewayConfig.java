package web.minda.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {

        return route("auth-service")
                .route(
                    path("/auth/**")
                        .or(path("/projectLoginpage/**"))
                        .or(path("/projectModuleDashboard/**")),
                    http()
                )
                .before(uri("https://auth-service:8091"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {

        return route("user-service")
                .route(
                    path("/user/**"),
                    http()
                )
                .before(uri("https://user-service:8092"))
                .build();
    }
}