package com.imo.backend.models.comments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentDTO {

  @NotBlank(message = "O comentario nao pode ser vazio")
  @Size(max = 300, message = "O seu comentario precisa ter menos de 300 caracteres")
  private String comment;

  private String parentId;
}
