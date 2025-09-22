package com.imo.backend.modules.user.http.dtos.auth;

public record LoginResponseDTO(
    String accessToken,
    Long expiresIn
) {
}
