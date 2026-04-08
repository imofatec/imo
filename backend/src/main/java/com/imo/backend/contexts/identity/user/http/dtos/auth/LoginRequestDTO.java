package com.imo.backend.contexts.identity.user.http.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @NotBlank(message = "Preencha o email") @Email(message = "Insira um email valido") String email,
    @NotBlank(message = "Preencha a senha") String password) {}
