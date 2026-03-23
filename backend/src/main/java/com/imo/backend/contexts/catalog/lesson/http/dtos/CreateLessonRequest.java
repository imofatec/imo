package com.imo.backend.contexts.catalog.lesson.http.dtos;

import com.imo.backend.contexts.catalog.lesson.commands.CreateLessonCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateLessonRequest(
    @NotBlank(message = "Preencha o título da aula")
    @Size(min = 10, max = 50, message = "O título da aula precisa ter entre 10 a 50 caracteres")
    String title,

    @Size(max = 600, message = "A descrição da aula pode ter no máximo 600 caracteres")
    String description,

    @NotBlank(message = "Preencha o link da aula")
    @Pattern(regexp = "^(https://)?(www\\.)?(youtube\\.com/watch\\?v=)?[\\w-]{11}(&.*)?$", message = "Preencha um link do youtube válido ou um código de vídeo válido")
    String youtubeLink

) {
  public CreateLessonCommand toCommand() {
    return new CreateLessonCommand(this.title, this.description, this.youtubeLink);
  }

}
