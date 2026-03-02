package com.imo.backend.modules.course.actions.helpers;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.inputs.UpdateCourseByIdInput;
import com.imo.backend.modules.course.value_objects.Category;
import com.imo.backend.modules.course.value_objects.CourseName;
import com.imo.backend.modules.course.value_objects.Level;
import com.imo.backend.modules.lesson.Lesson;

public class CourseUpdater {
  public static void apply(Course course, UpdateCourseByIdInput input) {
    if (input.name() != null) {
      course.setName(new CourseName(input.name()));
    }

    if (input.category() != null) {
      course.setCategory(new Category(input.category()));
    }

    if (input.level() != null) {
      course.setLevel(new Level(input.level()));
    }

    if (input.description() != null) {
      course.setDescription(input.description());
    }

    if (input.lessonsCount() != null) {
      course.setLessonsCount(input.lessonsCount());
    }

    if (input.firstLessonYoutubeLink() != null) {
      course.setFirstLessonYoutubeLink(Lesson.formatYoutubeLink(input.firstLessonYoutubeLink()));
    }
  }
}
