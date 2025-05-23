package com.imo.backend.models.course.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FieldsToUpdateCourse {

  @Size(min = 10, max = 100, message = "O nome do curso precisa ter de 10 a 100 caracteres")
  private String name;

  private String category;

  private String level;

  @Size(min = 10, max = 300, message = "A descrição do curso precisa ter de 10 a 300 caracteres")
  private String description;

}
