package com.imo.backend.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "Preencha os campos vazios")
    @Email(message = "Insira um email valido")
    private String email;

    @NotBlank(message = "Preencha os campos vazios")
    private String password;
}
