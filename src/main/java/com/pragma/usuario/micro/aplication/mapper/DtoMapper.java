package com.pragma.usuario.micro.aplication.mapper;

import com.pragma.usuario.micro.aplication.dto.ClientDto;
import com.pragma.usuario.micro.aplication.dto.EmployeeDto;
import com.pragma.usuario.micro.aplication.dto.UserDto;
import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.infrastructure.persistence.usuario.UserData;

public class DtoMapper {

    private DtoMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static User convertUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .dateOfBirth(userDto.getDateOfBirth())
                .numberDocument(userDto.getNumberDocument())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .roleId(userDto.getRoleId())
                .build();
    }
    public static UserDto convertUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .numberDocument(user.getNumberDocument())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .roleId(user.getRoleId())
                .build();
    }
    public static UserData convertEmployeeDtoToUserData(EmployeeDto employeeDto) {
        return UserData.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .lastName(employeeDto.getLastName())
                .numberDocument(employeeDto.getNumberDocument())
                .password(employeeDto.getPassword())
                .phoneNumber(employeeDto.getPhoneNumber())
                .email(employeeDto.getEmail())
                .build();
    }
    public static EmployeeDto convertUserToEmployeeDto(User user) {
        return EmployeeDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .numberDocument(user.getNumberDocument())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .roleId(user.getRoleId())
                .build();
    }
    public static UserData convertClientDtoToUserData(ClientDto clientDto) {
        return UserData.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .lastName(clientDto.getLastName())
                .numberDocument(clientDto.getNumberDocument())
                .password(clientDto.getPassword())
                .phoneNumber(clientDto.getPhoneNumber())
                .email(clientDto.getEmail())
                .build();
    }
    public static ClientDto convertUserToClientDto(User user) {
        return ClientDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .numberDocument(user.getNumberDocument())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .roleId(user.getRoleId())
                .build();
    }

}
