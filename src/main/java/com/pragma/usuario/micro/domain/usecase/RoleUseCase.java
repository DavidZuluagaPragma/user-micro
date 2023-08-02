package com.pragma.usuario.micro.domain.usecase;

import com.pragma.usuario.micro.domain.model.rol.Role;
import com.pragma.usuario.micro.domain.model.rol.gateway.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class RoleUseCase {

    @Autowired
    RoleRepository roleRepository;

    public Mono<Role> findRole (Integer id){
        return roleRepository.findRole(id);
    }

    public Mono<Role> createRole(Role role){
        return roleRepository.createRole(role);
    }

}
