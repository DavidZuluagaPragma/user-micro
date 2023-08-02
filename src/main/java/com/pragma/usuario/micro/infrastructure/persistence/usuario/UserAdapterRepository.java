package com.pragma.usuario.micro.infrastructure.persistence.usuario;

import com.pragma.usuario.micro.aplication.dto.UserLoginDto;
import com.pragma.usuario.micro.aplication.mapper.DataMapper;
import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.domain.model.usuario.gateway.UserGateway;
import com.pragma.usuario.micro.infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserAdapterRepository implements UserGateway {

    @Autowired
    UserDataRepository repository;

    @Override
    public Mono<User> createUser(UserData userData) {
        return Mono.fromCallable(() -> repository.save(userData))
                .map(DataMapper::convertUserDatatoUser)
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<User> findUserById(String userId) {
        return Mono.fromCallable(() -> repository.findById(Integer.parseInt(userId)).orElse(UserData.builder().build()))
                .map(DataMapper::convertUserDatatoUser)
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Boolean> isOwner(String userId) {
        return Mono.fromCallable(() -> repository.findById(Integer.parseInt(userId)).get())
                .flatMap(userData -> {
                    if (userData.getRole() != null && userData.getRole().getName().equals("OWNER")) {
                        return Mono.just(Boolean.TRUE);
                    }
                    return Mono.just(Boolean.FALSE);
                })
                .onErrorResume(throwable -> Mono.error(new BusinessException(BusinessException.Type.ERROR_DATABASE_DATA_NOT_FOUND)));
    }

    @Override
    public Mono<UserLoginDto> findByEmail(String email) {
        return Mono.fromCallable(() -> repository.findByEmail(email).orElse(UserData.builder().build()))
                .map(DataMapper::convertUserDatatoUserLoginDto);
    }

    @Override
    public UserLoginDto findByUsername(String username) {
        return DataMapper.convertUserDatatoUserLoginDto(repository.findByEmail(username)
                .orElse(UserData.builder().build()));
    }

    public Mono<User> createEmployee(UserData userData) {
        return Mono.fromCallable(() -> repository.save(userData))
                .map(DataMapper::convertUserDatatoUser)
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<User> createClient(UserData userData) {
        return Mono.fromCallable(() -> repository.save(userData))
                .map(DataMapper::convertUserDatatoUser)
                .onErrorResume(Mono::error);
    }
}
