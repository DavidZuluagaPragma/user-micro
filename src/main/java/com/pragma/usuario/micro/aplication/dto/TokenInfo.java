package com.pragma.usuario.micro.aplication.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record TokenInfo(String jwtToken) {
}
