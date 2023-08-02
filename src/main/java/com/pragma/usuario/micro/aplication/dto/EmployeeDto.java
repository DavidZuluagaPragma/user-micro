package com.pragma.usuario.micro.aplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class EmployeeDto {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String lastName;
    @NonNull
    private String numberDocument;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private Integer roleId;
}
