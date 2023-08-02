package com.pragma.usuario.micro.infrastructure.security.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
