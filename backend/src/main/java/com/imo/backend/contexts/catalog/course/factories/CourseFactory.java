package com.imo.backend.contexts.catalog.course.factories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.inputs.CreateCourseInput;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseFactory {
  public static Course createCourse(CreateCourseInput input) {
    Course course = new Course();

    course.setActive(true);
    course.setContributorId(input.contributorId());
    course.setName(new CourseName(input.name()));
    course.setCategory(new Category(input.category()));
    course.setDescription(input.description());
    course.setLevel(new Level(input.level()));
    course.setFirstLessonYoutubeLink(input.lessons().getFirst().youtubeLink());
    course.setLessonsCount(input.lessons().size());

    return course;
  }
}
