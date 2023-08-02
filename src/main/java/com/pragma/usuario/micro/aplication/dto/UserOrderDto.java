package com.pragma.usuario.micro.aplication.dto;

import com.pragma.usuario.micro.domain.model.usuario.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserOrderDto {
    User client;
    User chef;
}
