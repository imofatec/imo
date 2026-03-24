package com.imo.backend.contexts.identity.recovery.http.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
    @Email(message = "Insira um email válido")
    @NotBlank(message = "Preencha o email")
    String email,

    @NotBlank(message = "Preencha a senha")
    @Pattern(regexp = "^[\\S]+$", message = "A senha não pode conter espaços em branco")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "A senha precisa ter no mínimo uma letra maiúscula e 1 número")
    @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres")
    @Size(max = 50, message = "A senha não pode ter mais do que 50 caracteres")
    String newPassword,

    @NotBlank
    String code
) {
}
