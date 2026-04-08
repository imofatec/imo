package com.imo.backend.contexts.identity.recovery.http.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyRecoveryCodeRequest(
    @Email(message = "Insira um email válido") @NotBlank(message = "Preencha o email") String email,
    @NotBlank(message = "Preencha o código") String code) {}
