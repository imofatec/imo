package com.imo.backend.contexts.identity.http.dtos.auth;

public record LoginResponseDTO(
    String accessToken,
    Long expiresIn
) {
}
