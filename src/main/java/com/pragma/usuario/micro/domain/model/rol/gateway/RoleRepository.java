package com.pragma.usuario.micro.domain.model.rol.gateway;

import com.pragma.usuario.micro.domain.model.rol.Role;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository {
    Mono<Role> findRole(Integer id);
    Mono<Role> createRole(Role role);

}
