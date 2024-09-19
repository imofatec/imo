package com.imo.backend.models.lessons.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateLessonDto {
    @NotBlank(message = "Preencha o título da aula")
    @Size(min = 10, max = 50, message = "O título da aula precisa ter entre 10 a 50 caracteres")
    private String title;

    @Size(max = 600, message = "A descrição da aula pode ter no máximo 600 caracteres")
    private String description;

    @NotBlank(message = "Preencha o link da aula")
    private String youtubeLink;
}
