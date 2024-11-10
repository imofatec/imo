package com.imo.backend.models.user.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotBlank(message = "Preencha o seu nome")
//    @Pattern(regexp = "^[\\S]+$", message = "Username não pode conter espaços em branco")
    @Size(min = 5, max = 20, message = "O seu nome precisa ter no mínimo 5 caracteres")
    private String name;

    @Email(message = "Insira um email válido")
    @NotBlank(message = "Preencha o email")
    private String email;

    @NotBlank(message = "Preencha a senha")
    @Pattern(regexp = "^[\\S]+$", message = "A senha não pode conter espaços em branco")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "A senha precisa ter no mínimo uma letra maiúscula e 1 número")
    @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres")
    @Size(max = 50, message = "A senha não pode ter mais do que 50 caracteres")
    private String password;

    @NotBlank(message = "Preencha o campo de confirmar senha")
    private String confPassword;
}
