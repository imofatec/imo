package com.imo.backend.models.course.dtos;

import com.imo.backend.models.lessons.Lesson;
import lombok.Data;

import java.util.List;

@Data
public class FieldsToUpdateCourse {

  private String name;

  private String level;

  private String category;

  private String description;

  private List<Lesson> lessons;

}
