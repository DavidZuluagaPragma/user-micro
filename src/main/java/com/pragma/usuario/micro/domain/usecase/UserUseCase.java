package com.pragma.usuario.micro.domain.usecase;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.pragma.usuario.micro.aplication.dto.*;
import com.pragma.usuario.micro.aplication.mapper.DataMapper;
import com.pragma.usuario.micro.aplication.mapper.DtoMapper;
import com.pragma.usuario.micro.domain.model.common.UserValidation;
import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.domain.model.usuario.gateway.UserGateway;
import com.pragma.usuario.micro.infrastructure.exceptions.CustomException;
import com.pragma.usuario.micro.infrastructure.persistence.rol.RoleData;
import com.pragma.usuario.micro.infrastructure.security.dto.SignInDto;
import com.pragma.usuario.micro.infrastructure.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserUseCase {

    @Autowired
    RoleUseCase roleUseCase;

    @Autowired
    UserGateway userGateway;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    @Autowired
    AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Mono<User> findUser(String userId) {
        return userGateway.findUserById(userId);
    }

    public Mono<UserDto> createUser(User user) {
        return UserValidation.userValidation(user)
                .flatMap(validUser -> roleUseCase.findRole(validUser.getRoleId()))
                .flatMap(role -> userGateway.createUser(DataMapper.convertUsertoUserData(user)
                        .toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(RoleData.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .description(role.getDescription())
                                .build())
                        .build()))
                .map(userFinal -> {
                    try {
                        this.createAwsUser(user.getEmail(), user.getPassword());
                    } catch (CustomException e) {
                        Mono.error(new CustomException(e.getStatus(),e.getMessage()));
                    }
                    return  userFinal.toBuilder().password(user.getPassword()).build();
                })
                .map(DtoMapper::convertUserToUserDto);
    }

    public Mono<Boolean> isOwner(String userId) {
        return userGateway.isOwner(userId);
    }

    public Mono<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
        return userGateway.createEmployee(DtoMapper
                        .convertEmployeeDtoToUserData(employeeDto
                                .toBuilder()
                                .password(passwordEncoder.encode(employeeDto.getPassword()))
                                .build())
                        .toBuilder()
                        .role(RoleData.builder()
                                .id(3)
                                .build())
                        .build())
                .map(user -> {
                    try {
                        this.createAwsUser(user.getEmail(), user.getPassword());
                    } catch (CustomException e) {
                        Mono.error(new CustomException(e.getStatus(),e.getMessage()));
                    }
                    return user.toBuilder().password(employeeDto.getPassword()).build();
                })
                .map(DtoMapper::convertUserToEmployeeDto);
    }

    public Mono<ClientDto> createClient(ClientDto clientDto) {
        return userGateway.createClient(DtoMapper
                        .convertClientDtoToUserData(clientDto.toBuilder()
                                .password(passwordEncoder.encode(clientDto.getPassword()))
                                .build())
                        .toBuilder()
                        .role(RoleData.builder()
                                .id(4)
                                .build())
                        .build())
                .map(user -> user.toBuilder().password(clientDto.getPassword()).build())
                .map(DtoMapper::convertUserToClientDto);
    }

    public Mono<UserOrderDto> getUsersFromOrder(UserOrderRequestDto orderRequestDto) {
        return userGateway.findUserById(orderRequestDto.getChef().toString())
                .flatMap(chef -> userGateway.findUserById(orderRequestDto.getClient().toString())
                        .map(client -> UserOrderDto.builder()
                                .chef(chef.getId() != null ? chef : User.builder().build())
                                .client(client.getId() != null ? client : User.builder().build())
                                .build()));
    }

    public void createAwsUser(String email, String password) throws CustomException {
        try{
            AttributeType attributeType = new AttributeType().withName("email").withValue(email);
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.withClientId(clientId)
                    .withPassword(password)
                    .withUsername(email)
                    .withUserAttributes(attributeType);
            awsCognitoIdentityProvider.signUp(signUpRequest);
        }catch (Exception e){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,"ERROR");
        }
    }

    public Mono<TokenDto> signInUser(SignInDto signInDto){
        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", signInDto.getEmail());
        authParams.put("PASSWORD", signInDto.getPassword());

        InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(clientId)
                .withAuthParameters(authParams);

        InitiateAuthResult initiateAuthResult = awsCognitoIdentityProvider.initiateAuth(initiateAuthRequest);

        UserLoginDto userLoginDto = userGateway.findByUsername(signInDto.getEmail());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userLoginDto.getEmail(), userLoginDto.getPassword(),
                List.of(new SimpleGrantedAuthority(userLoginDto.getRole().getName())));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return Mono.just(new TokenDto(initiateAuthResult.getAuthenticationResult().getIdToken()));
    }

}
