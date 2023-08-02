package com.pragma.usuario.micro.infrastructure.endpoint;

import com.pragma.usuario.micro.domain.model.rol.Role;
import com.pragma.usuario.micro.domain.usecase.RoleUseCase;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    @Autowired
    RoleUseCase useCase;

    @PostMapping("/create")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Mono<Role> createRole(@RequestBody Role role){
        return useCase.createRole(role);
    }

}
