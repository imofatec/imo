package com.imo.backend.contexts.identity.actions.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordInput {

  @NotBlank(message = "Preencha a senha")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "A senha precisa ter no mínimo uma letra maiúscula e 1 número")
  @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres")
  @Size(max = 50, message = "A senha não pode ter mais do que 50 caracteres")
  String password;
}
