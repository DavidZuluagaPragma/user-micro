package com.pragma.usuario.micro.domain.model.usuario;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Integer id;
    private String name;
    private String lastName;
    private String numberDocument;
    private String phoneNumber;
    private String dateOfBirth;
    private String email;
    private String password;
    private Integer roleId;
}
