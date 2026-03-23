package com.imo.backend.contexts.social.comment.http.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
    @NotBlank(message = "O comentario nao pode ser vazio")
    @Size(max = 300, message = "O seu comentario precisa ter menos de 300 caracteres")
    String content,

    String parentId
) {
}
