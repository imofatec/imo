package com.imo.backend.modules.course.actions.inputs;

import jakarta.validation.constraints.Size;

public record UpdateCourseInput(
    @Size(min = 10, max = 100, message = "O nome do curso precisa ter de 10 a 100 caracteres")
    String name,

    String category,

    String level,

    @Size(min = 10, max = 300, message = "A descrição do curso precisa ter de 10 a 300 caracteres")
    String description,

    Integer lessonsCount
) {
}
