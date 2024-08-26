package com.imo.backend.models.dto;

public record LoginResponse(String acessToken, Long expiresIn) {
}
