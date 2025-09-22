package com.imo.backend.modules.course.http.dtos;

import com.imo.backend.modules.lesson.actions.inputs.CreateLessonInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateCourseRequest(
    @NotBlank(message = "Preencha o nome do curso")
    @Size(min = 10, max = 100, message = "O nome do curso precisa ter de 10 a 100 caracteres")
    String name,

    @NotBlank(message = "Preencha a categoria do curso")
    String category,

    @NotBlank(message = "Preencha o nível do curso")
    String level,

    @NotBlank(message = "Preencha a descrição do curso")
    @Size(min = 10, max = 300, message = "A descrição do curso precisa ter de 10 a 300 caracteres")
    String description,

    @Size(min = 1, max = 100, message = "Um curso pode ter no mínimo 1 e no máximo 100 aulas")
    @Valid
    List<CreateLessonInput> lessons
) {
}
