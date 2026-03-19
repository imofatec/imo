package com.imo.backend.contexts.catalog.course.actions.helpers;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;
import com.imo.backend.contexts.catalog.lesson.Lesson;

public class CourseUpdater {
  public static void apply(Course course, UpdateCourseByIdCommand command) {
    if (command.name() != null) {
      course.setName(new CourseName(command.name()));
    }

    if (command.category() != null) {
      course.setCategory(new Category(command.category()));
    }

    if (command.level() != null) {
      course.setLevel(new Level(command.level()));
    }

    if (command.description() != null) {
      course.setDescription(command.description());
    }

    if (command.lessonsCount() != null) {
      course.setLessonsCount(command.lessonsCount());
    }

    if (command.firstLessonYoutubeLink() != null) {
      course.setFirstLessonYoutubeLink(Lesson.formatYoutubeLink(command.firstLessonYoutubeLink()));
    }
  }
}
