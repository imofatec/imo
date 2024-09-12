package com.imo.backend.models.user.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterUserRequest {

    @NotBlank(message = "Preencha o username")
    @Pattern(regexp = "^[\\S]+$", message = "Username nao pode conter espaços em branco")
    @Size(min = 5, max = 20, message = "O username precisa ter de 5 a 20 caracteres")
    private String username;

    @Email(message = "Insira um email valido")
    @NotBlank(message = "Preencha o email")
    private String email;

    @NotBlank(message = "Preencha a senha")
    @Size(min = 5, message = "A senha precisa de no mínimo 8 caracteres")
    @Size(max = 50, message = "A senha não pode ter mais do que 50 caracters")
    @Pattern(regexp = "^[\\S]+$", message = "A senha nao pode conter espaços em branco")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "A senha precisa ter no mínimo uma letra maiúscula e 1 número\nExemplo: Minhasenha1")
    private String password;

    @NotBlank(message = "Preencha o campo de confirmar senha")
    private String confPassword;
}
