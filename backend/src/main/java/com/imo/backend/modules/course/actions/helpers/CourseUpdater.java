package com.imo.backend.modules.course.actions.helpers;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.inputs.UpdateCourseInput;
import com.imo.backend.modules.course.value_objects.Category;
import com.imo.backend.modules.course.value_objects.CourseName;
import com.imo.backend.modules.course.value_objects.Level;

public class CourseUpdater {
  public static void apply(Course course, UpdateCourseInput dto) {
    if (dto.name() != null) {
      course.setName(new CourseName(dto.name()));
    }

    if (dto.category() != null) {
      course.setCategory(new Category(dto.category()));
    }

    if (dto.level() != null) {
      course.setLevel(new Level(dto.level()));
    }

    if (dto.description() != null) {
      course.setDescription(dto.description());
    }

    if (dto.lessonsCount() != null) {
      course.setLessonsCount(dto.lessonsCount());
    }
  }
}
