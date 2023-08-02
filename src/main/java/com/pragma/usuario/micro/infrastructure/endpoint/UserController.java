package com.pragma.usuario.micro.infrastructure.endpoint;

import com.pragma.usuario.micro.aplication.dto.*;
import com.pragma.usuario.micro.aplication.mapper.DtoMapper;
import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.domain.usecase.UserUseCase;
import com.pragma.usuario.micro.infrastructure.security.dto.SignInDto;
import com.pragma.usuario.micro.infrastructure.security.dto.TokenDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserUseCase useCase;


    @PostMapping("/sign-in")
    public Mono<TokenDto> signIn(@RequestBody SignInDto signInDto){
        return useCase.signInUser(signInDto);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Mono<User> findUserById(@PathVariable String userId){
        return useCase.findUser(userId);
    }
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Mono<UserDto> createUser(@RequestBody UserDto userDto){
        return useCase.createUser(DtoMapper.convertUserDtoToUser(userDto));
    }
    @GetMapping("/owner/{userId}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Mono<Boolean> isOwner(@PathVariable String userId){
        return useCase.isOwner(userId);
    }
    @PostMapping("/create-employee")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Mono<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        return useCase.createEmployee(employeeDto);
    }
    @PostMapping("/create-client")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Mono<ClientDto> createClient(@RequestBody ClientDto clientDto){
        return useCase.createClient(clientDto);
    }
    @PostMapping("/orders")
    @PreAuthorize("hasAuthority('EMPLOYEE')  or hasAuthority('OWNER')")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Mono<UserOrderDto> getUsersFromOrder(@RequestBody UserOrderRequestDto orderRequestDto){
        return useCase.getUsersFromOrder(orderRequestDto);
    }

}
