package com.imo.backend.models.user.dtos;

public record LoginResponse(String acessToken, Long expiresIn) {
}
