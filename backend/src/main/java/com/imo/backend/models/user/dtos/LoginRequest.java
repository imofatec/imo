package com.imo.backend.models.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "Preencha o email")
    @Email(message = "Insira um email valido")
    private String email;

    @NotBlank(message = "Preencha a senha")
    private String password;
}
