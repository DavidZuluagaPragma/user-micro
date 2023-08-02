package com.pragma.usuario.micro.aplication.dto;

import com.pragma.usuario.micro.domain.model.rol.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserLoginDto {
    private Integer id;
    private String email;
    private String password;
    private Role role;
}
