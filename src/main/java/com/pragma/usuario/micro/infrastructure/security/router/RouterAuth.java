package com.pragma.usuario.micro.infrastructure.security.router;

import com.pragma.usuario.micro.infrastructure.security.handler.AuthHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class RouterAuth {

    private static final String PATH = "auth/";

    @Bean
    RouterFunction<ServerResponse> authRouter(AuthHandler handler) {
        return RouterFunctions.route()
                .POST(PATH + "login", handler::login)
                .build();
    }
}
