package com.imo.backend.models.comments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentDTO {

  @NotBlank(message = "O comentario nao pode ser vazio")
  @Size(max = 50, message = "O seu comentario precisa ter menos de 100 caracteres")
  private String comment;
}
