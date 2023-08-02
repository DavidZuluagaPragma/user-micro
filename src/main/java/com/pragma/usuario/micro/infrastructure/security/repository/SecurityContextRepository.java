package com.pragma.usuario.micro.infrastructure.security.repository;

import com.pragma.usuario.micro.aplication.dto.UserLoginDto;
import com.pragma.usuario.micro.domain.model.usuario.gateway.UserGateway;
import com.pragma.usuario.micro.infrastructure.security.jwt.JwtAuthenticationManager;
import com.pragma.usuario.micro.infrastructure.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager jwtAuthenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserGateway userGateway;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getAttribute("token");
        String authenticationToken = exchange.getAttribute("authenticationToken");
        return jwtAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, authenticationToken))
                .map(SecurityContextImpl::new);
    }
}
