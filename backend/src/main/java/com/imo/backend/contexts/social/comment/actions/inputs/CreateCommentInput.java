package com.imo.backend.contexts.social.comment.actions.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentInput(
    @NotBlank(message = "O comentario nao pode ser vazio")
        @Size(max = 300, message = "O seu comentario precisa ter menos de 300 caracteres")
        String content,
    String parentId) {}
