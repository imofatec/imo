package com.imo.backend.contexts.catalog.course.factories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.CreateCourseCommand;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseFactory {
  public static Course createCourse(CreateCourseCommand command) {
    Course course = new Course();

    course.setActive(true);
    course.setContributorId(command.contributorId());
    course.setName(new CourseName(command.name()));
    course.setCategory(new Category(command.category()));
    course.setDescription(command.description());
    course.setLevel(new Level(command.level()));
    course.setFirstLessonYoutubeLink(command.lessons().getFirst().youtubeLink());
    course.setLessonsCount(command.lessons().size());

    return course;
  }
}
