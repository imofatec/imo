package com.imo.backend.models.course.dtos;

import com.imo.backend.models.lessons.Lesson;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreateCourseDto {

    @NotBlank(message = "Preencha os campos vazios")
    private String name;

    @NotBlank(message = "Preencha os campos vazios")
    private String contributor;

    @NotBlank(message = "Preencha os campos vazios")
    private String category;

    @NotBlank(message = "Preencha os campos vazios")
    private String description;

    private List<Lesson> lessons;
}
