package com.pragma.usuario.micro.infrastructure.security.jwt;

import com.pragma.usuario.micro.aplication.dto.UserLoginDto;
import com.pragma.usuario.micro.domain.model.usuario.gateway.UserGateway;
import com.pragma.usuario.micro.infrastructure.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class JwtFilter implements WebFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserGateway userGateway;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (path.contains("auth"))
            return chain.filter(exchange);
        if (path.contains("sign-in"))
            return chain.filter(exchange);
        if (path.contains("swagger") || path.contains("api-docs"))
            return chain.filter(exchange);
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null)
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "no token was found"));
        if (!auth.startsWith("Bearer "))
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "invalid auth"));
        String token = auth.replace("Bearer ", "");
        if (token != null && jwtProvider.validateToken(token)) {
            String email = jwtProvider.getUserNameFromToken(token);
            UserLoginDto userLoginDto = userGateway.findByUsername(email);
            UserDetails userDetails = new User(userLoginDto.getEmail(), userLoginDto.getPassword(),
                    List.of(new SimpleGrantedAuthority(userLoginDto.getRole().getName())));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            exchange.getAttributes().put("authenticationToken", jwtProvider.generateToken(userDetails));
        }

        exchange.getAttributes().put("token", token);
        return chain.filter(exchange);
    }
}
