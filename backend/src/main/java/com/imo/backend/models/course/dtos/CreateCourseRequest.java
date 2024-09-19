package com.imo.backend.models.course.dtos;

import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateCourseRequest {

    @NotBlank(message = "Preencha o nome do curso")
    @Size(min = 10, max = 100, message = "O nome do curso precisa ter de 10 a 100 caracteres")
    private String name;

    @NotBlank(message = "Preencha a categoria do curso")
    private String category;

    @NotBlank(message = "Preencha a descrição do curso")
    @Size(min = 10, max = 300, message = "A descrição do curso precisa ter de 10 a 300 caracteres")
    private String description;

    @Size(min = 1, max = 100, message = "Um curso pode ter no mínimo 1 e no máximo 100 aulas")
    @Valid
    private List<CreateLessonDto> lessons;
}
