package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import jakarta.validation.constraints.Size;

public record UpdateCourseByIdRequest(
    @Size(min = 10, max = 100, message = "O nome do curso precisa ter de 10 a 100 caracteres")
    String name,

    Categories category,

    String level,

    @Size(min = 10, max = 300, message = "A descrição do curso precisa ter de 10 a 300 caracteres")
    String description
) {
  public UpdateCourseByIdCommand toCommand(String id) {
    return new UpdateCourseByIdCommand(
        id,
        this.name,
        this.category,
        this.level,
        this.description,
        null,
        null
    );
  }
}
