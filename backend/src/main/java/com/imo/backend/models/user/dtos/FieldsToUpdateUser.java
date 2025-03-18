package com.imo.backend.models.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class FieldsToUpdateUser {

    @Email(message = "Insira um email válido")
    String email;

//    @Pattern(regexp = "^[\\S]+$", message = "Username não pode conter espaços em branco")
    @Size(min = 5, max = 20, message = "O seu nome precisa ter no mínimo 5 caracteres")
    String name;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "A senha precisa ter no mínimo uma letra maiúscula e 1 número")
    @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres")
    @Size(max = 50, message = "A senha não pode ter mais do que 50 caracteres")
    String password;

}
