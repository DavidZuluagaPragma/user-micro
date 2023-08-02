package com.pragma.usuario.micro.infrastructure.security.service;

import com.pragma.usuario.micro.domain.model.usuario.gateway.UserGateway;
import com.pragma.usuario.micro.infrastructure.security.dto.LoginDto;
import com.pragma.usuario.micro.infrastructure.security.dto.TokenDto;
import com.pragma.usuario.micro.infrastructure.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserGateway userGateway;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public Mono<TokenDto> login(LoginDto dto) {
        return Mono.empty();
        /*return userGateway.findByEmail(dto.getUsername())
                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .map(usuario -> new User(usuario.getEmail(), usuario.getPassword(),
                        List.of(new SimpleGrantedAuthority(usuario.getRole().getName()))))
                .map(user -> {
                    UserLoginDto userLoginDto = userGateway.findByUsername(dto.getUsername());
                    UserDetails userDetails = new User(userLoginDto.getEmail(), userLoginDto.getPassword(),
                            List.of(new SimpleGrantedAuthority(userLoginDto.getRole().getName())));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    return new TokenDto(jwtProvider.generateToken(user))
                })
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "bad credentials")));*/
    }

}
