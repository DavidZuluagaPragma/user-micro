package com.pragma.usuario.micro.domain.model.usuario.gateway;

import com.pragma.usuario.micro.aplication.dto.UserLoginDto;
import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.infrastructure.persistence.usuario.UserData;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserGateway {
    Mono<User> createUser(UserData userData);
    Mono<User> findUserById(String userId);
    Mono<Boolean> isOwner(String userId);
    Mono<User> createEmployee(UserData userData);
    Mono<User> createClient(UserData userData);
    Mono<UserLoginDto> findByEmail(String email);
    UserLoginDto findByUsername(String username);
}
