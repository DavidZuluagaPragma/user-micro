package com.pragma.usuario.micro.aplication.mapper;

import com.pragma.usuario.micro.aplication.dto.UserLoginDto;
import com.pragma.usuario.micro.domain.model.rol.Role;
import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.infrastructure.persistence.rol.RoleData;
import com.pragma.usuario.micro.infrastructure.persistence.usuario.UserData;

public class DataMapper {

    private DataMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static User convertUserDatatoUser (UserData userData) {
        return User.builder()
                .id(userData.getId())
                .name(userData.getName())
                .lastName(userData.getLastName())
                .dateOfBirth(userData.getDateOfBirth())
                .numberDocument(userData.getNumberDocument())
                .password(userData.getPassword())
                .phoneNumber(userData.getPhoneNumber())
                .email(userData.getEmail())
                .roleId(userData.getRole() != null ? userData.getRole().getId() : null)
                .build();
    }

    public static UserData convertUsertoUserData (User user) {
        return UserData.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .numberDocument(user.getNumberDocument())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    public static Role convertRoleDatatoRole (RoleData roleData) {
        return Role.builder()
                .id(roleData.getId())
                .name(roleData.getName())
                .description(roleData.getDescription())
                .build();
    }

    public static RoleData convertRoletoRoleData (Role role) {
        return RoleData.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    public static UserLoginDto convertUserDatatoUserLoginDto(UserData userData) {
        return UserLoginDto.builder()
                .id(userData.getId())
                .password(userData.getPassword())
                .email(userData.getEmail())
                .role(convertRoleDatatoRole(userData.getRole()))
                .build();
    }

}
