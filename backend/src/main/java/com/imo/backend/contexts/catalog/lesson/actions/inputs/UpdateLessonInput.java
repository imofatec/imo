package com.imo.backend.contexts.catalog.lesson.actions.inputs;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateLessonInput(
    @Size(min = 10, max = 50, message = "O título da aula precisa ter entre 10 a 50 caracteres")
    String title,

    @Size(max = 600, message = "A descrição da aula pode ter no máximo 600 caracteres")
    String description,

    @Pattern(regexp = "^(https://)?(www\\.)?(youtube\\.com/watch\\?v=)?[\\w-]{11}(&.*)?$", message =
        "Preencha um link do youtube válido, "
        + "Ou um código de video válido (aquilo que vem após watch?v=)")
    String youtubeLink
) {
}
