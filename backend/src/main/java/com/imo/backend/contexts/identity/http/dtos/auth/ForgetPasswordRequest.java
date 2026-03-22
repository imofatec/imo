package com.imo.backend.contexts.identity.http.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgetPasswordRequest(
    @Email(message = "Insira um email válido")
    @NotBlank(message = "Preencha o email")
    String email
) {
}