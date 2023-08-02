package com.pragma.usuario.micro.aplication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
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
    private String dateOfBirth;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private Integer roleId;
}
