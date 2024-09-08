package com.imo.backend.models.course.dtos;


import com.imo.backend.models.lessons.Lesson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateCourseDto {

    @NotBlank(message = "Preencha o nome do curso")
    private String name;

    @NotBlank(message = "Preencha o nome do contribuidor do curso")
    private String contributor;

    @NotBlank(message = "Preencha a categoria do curso")
    private String category;

    @NotBlank(message = "Preencha a descrição do curso")
    private String description;

    @Valid
    private List<Lesson> lessons;
}
