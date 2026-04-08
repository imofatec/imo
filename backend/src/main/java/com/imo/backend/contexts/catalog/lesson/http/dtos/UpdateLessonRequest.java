package com.imo.backend.contexts.catalog.lesson.http.dtos;

import com.imo.backend.contexts.catalog.lesson.commands.UpdateLessonCommand;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateLessonRequest(
    @Size(min = 10, max = 50, message = "O título da aula precisa ter entre 10 a 50 caracteres")
        String title,
    @Size(max = 600, message = "A descrição da aula pode ter no máximo 600 caracteres")
        String description,
    @Pattern(
            regexp = "^(https://)?(www\\.)?(youtube\\.com/watch\\?v=)?[\\w-]{11}(&.*)?$",
            message = "Preencha um link do youtube válido ou um código de vídeo válido")
        String youtubeLink) {
  // Une o ID da URL com os dados do Body
  public UpdateLessonCommand toCommand(String lessonId) {
    return new UpdateLessonCommand(lessonId, this.title(), this.description(), this.youtubeLink());
  }
}
