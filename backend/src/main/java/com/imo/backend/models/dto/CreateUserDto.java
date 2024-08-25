package com.imo.backend.models.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
public class CreateUserDto {

    @NotBlank(message = "Preencha os campos vazios")
    @NotNull(message = "Nao pode ser nulo")
    @NotEmpty(message = "Nao pode ser vazio")
    @Pattern(regexp = "^[\\S]+$", message = "Username nao pode conter espaços em branco")
    private String username;

    @Email(message = "Insira um email valido")
    @NotBlank(message = "Preencha os campos vazios")
    private String email;

    @NotBlank(message = "Preencha os campos vazios")
    private String password;

    private String confPassword;
}
