package com.pragma.usuario.micro.aplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserOrderRequestDto {
    private Integer chef;
    private Integer client;
}