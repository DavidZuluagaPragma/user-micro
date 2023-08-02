package com.pragma.usuario.micro.infrastructure.persistence.rol;

import com.pragma.usuario.micro.aplication.mapper.DataMapper;
import com.pragma.usuario.micro.domain.model.rol.Role;
import com.pragma.usuario.micro.domain.model.rol.gateway.RoleRepository;
import com.pragma.usuario.micro.infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RoleAdapterRepository implements RoleRepository {

    @Autowired
    RoleDataRepository repository;

    @Override
    public Mono<Role> findRole(Integer id) {
        return Mono.fromCallable(() -> repository.findById(id).get())
                .map(DataMapper::convertRoleDatatoRole)
                .onErrorResume(throwable -> Mono.error(new BusinessException(BusinessException.Type.ERROR_DATABASE_DATA_NOT_FOUND)));
    }

    @Override
    public Mono<Role> createRole(Role role) {
        return Mono.fromCallable(() -> repository.save(DataMapper.convertRoletoRoleData(role)))
                .map(DataMapper::convertRoleDatatoRole)
                .onErrorResume(Mono::error);
    }
}
