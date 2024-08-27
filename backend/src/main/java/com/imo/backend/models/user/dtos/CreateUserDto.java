package com.imo.backend.models.user.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateUserDto {

    @NotBlank(message = "Preencha os campos vazios")
    @Pattern(regexp = "^[\\S]+$", message = "Username nao pode conter espaços em branco")
    private String username;

    @Email(message = "Insira um email valido")
    @NotBlank(message = "Preencha os campos vazios")
    private String email;

    @NotBlank(message = "Preencha os campos vazios")
    private String password;

    private String confPassword;
}
