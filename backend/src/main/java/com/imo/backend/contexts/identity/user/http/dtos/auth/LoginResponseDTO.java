package com.imo.backend.contexts.identity.user.http.dtos.auth;

public record LoginResponseDTO(
    String accessToken,
    Long expiresIn
) {
}
