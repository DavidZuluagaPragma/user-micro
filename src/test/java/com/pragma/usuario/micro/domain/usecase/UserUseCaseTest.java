package com.pragma.usuario.micro.domain.usecase;

import com.pragma.usuario.micro.aplication.dto.ClientDto;
import com.pragma.usuario.micro.aplication.dto.EmployeeDto;
import com.pragma.usuario.micro.aplication.dto.UserDto;
import com.pragma.usuario.micro.domain.model.rol.Role;
import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.domain.model.usuario.gateway.UserGateway;
import com.pragma.usuario.micro.infrastructure.persistence.rol.RoleData;
import com.pragma.usuario.micro.infrastructure.persistence.usuario.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UserUseCase userUseCase;

    @Mock
    private RoleUseCase roleUseCase;

    @Test
    void successfulUserCreation() {
        var role = Role.builder()
                .id(1)
                .name("OWNER")
                .description("OWNER")
                .build();

        var user = User.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .dateOfBirth("13/04/2000")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(1)
                .build();

        var expectedUser = User.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .dateOfBirth("13/04/2000")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(1)
                .build();

        var userData = UserData.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .dateOfBirth("13/04/2000")
                .email("zuluroa@gmail.com")
                .password("123456")
                .role(RoleData.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .description(role.getDescription())
                        .build())
                .build();

        var userResult = UserDto.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .dateOfBirth("13/04/2000")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(1)
                .build();

        Mockito.when(roleUseCase.findRole(user.getRoleId())).thenReturn(Mono.just(role));
        Mockito.when(userGateway.createUser(userData)).thenReturn(Mono.just(expectedUser));

        var result = userUseCase.createUser(user);

        StepVerifier.create(result)
                .expectNext(userResult)
                .expectComplete();
    }

    @Test
    void findSuccessfulUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var user = User.builder()
                .id(1)
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .dateOfBirth("13/04/2000")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(1)
                .build();
        var expectedUser = User.builder()
                .id(1)
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .dateOfBirth("13/04/2000")
                .email("zuluroa@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .roleId(1)
                .build();
        Mockito.when(userGateway.findUserById(user.getId().toString())).thenReturn(Mono.just(expectedUser));
        var result = userUseCase.findUser(user.getId().toString());
        StepVerifier.create(result)
                .expectNext(expectedUser)
                .expectComplete()
                .verify();
    }

    @Test
    void isOwnerValidate() {
        Mockito.when(userGateway.isOwner("1")).thenReturn(Mono.just(Boolean.TRUE));
        var result = userUseCase.isOwner("1");
        StepVerifier.create(result)
                .expectNext(Boolean.TRUE)
                .expectComplete()
                .verify();
    }

    @Test
    void notOwnerValidate() {
        Mockito.when(userGateway.isOwner("1")).thenReturn(Mono.just(Boolean.FALSE));
        var result = userUseCase.isOwner("1");
        StepVerifier.create(result)
                .expectNext(Boolean.FALSE)
                .expectComplete()
                .verify();
    }

    /*@Test
    void successfulCreateEmployee () {

        RoleData roleData = RoleData.builder()
                .id(3)
                .build();

        UserData userData = UserData.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password("123456")
                .role(roleData)
                .build();

        User expectUser = User.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(roleData.getId())
                .build();

        EmployeeDto employeeDto = EmployeeDto.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(roleData.getId())
                .build();

        EmployeeDto employeeDtoEsperado = EmployeeDto.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(roleData.getId())
                .build();

        Mockito.when(userGateway.createEmployee(userData)).thenReturn(Mono.just(expectUser));

        var result = userUseCase.createEmployee(employeeDto);

        StepVerifier.create(result)
                .expectNext(employeeDtoEsperado)
                .expectComplete()
                .verify();
    }

    @Test
    void successfulCreateClient() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        RoleData roleData = RoleData.builder()
                .id(3)
                .build();

        UserData userData = UserData.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password("123456")
                .role(roleData)
                .build();

        User expectUser = User.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .roleId(roleData.getId())
                .build();

        ClientDto clientDto = ClientDto.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(roleData.getId())
                .build();

        ClientDto clientDtoEsperado = ClientDto.builder()
                .name("JESUS")
                .lastName("ZULUAGA")
                .numberDocument("1002201980")
                .phoneNumber("+573202040834")
                .email("zuluroa@gmail.com")
                .password("123456")
                .roleId(roleData.getId())
                .build();

        Mockito.when(userGateway.createClient(userData)).thenReturn(Mono.just(expectUser));

        var result = userUseCase.createClient(clientDto);

        StepVerifier.create(result)
                .expectNext(clientDtoEsperado)
                .expectComplete()
                .verify();
    }*/

}