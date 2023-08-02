package com.pragma.usuario.micro.domain.usecase;

import com.pragma.usuario.micro.domain.model.rol.Role;
import com.pragma.usuario.micro.domain.model.rol.gateway.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {

    @InjectMocks
    private RoleUseCase useCase;

    @Mock
    private RoleRepository repository;

    @Test
    void successfulRoleCreation(){
        var role = Role.builder()
                .id(1)
                .name("OWNER")
                .description("OWNER")
                .build();
        Mockito.when(repository.createRole(role)).thenReturn(Mono.just(role));
        var result = useCase.createRole(role);
        StepVerifier.create(result)
                .expectNext(role)
                .expectComplete()
                .verify();
    }
    @Test
    void successfulRoleFinding(){
        var role = Role.builder()
                .id(1)
                .name("OWNER")
                .description("OWNER")
                .build();
        Mockito.when(repository.findRole(1)).thenReturn(Mono.just(role));
        var result = useCase.findRole(1);
        StepVerifier.create(result)
                .expectNext(role)
                .expectComplete()
                .verify();
    }

}