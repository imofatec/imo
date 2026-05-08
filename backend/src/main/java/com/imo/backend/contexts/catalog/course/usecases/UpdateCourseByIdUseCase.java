package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.*;
import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.events.CourseSkillIdsChangedEvent;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.Slug;
import java.util.List;
import java.util.Set;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdUseCase {

  private final CourseRepository courseRepository;
  private final CoursePolicies coursePolicies;
  private final ApplicationEventPublisher applicationEventPublisher;

  public UpdateCourseByIdUseCase(
      CourseRepository courseRepository,
      CoursePolicies coursePolicies,
      ApplicationEventPublisher applicationEventPublisher) {
    this.courseRepository = courseRepository;
    this.coursePolicies = coursePolicies;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public Course execute(String courseId, UpdateCourseByIdCommand cmd) {
    Course course = this.courseRepository.findByIdOrThrow(courseId);
    List<String> previousSkillIds = course.getSkillIdsAsString();
    boolean skillIdsChanged = false;

    if (cmd.name() != null) {
      this.coursePolicies.checkSlugConflict(
          course.getContributorId(), Slug.create(cmd.name()), courseId);

      course.setName(new CourseName(cmd.name()));
    }

    if (cmd.category() != null) {
      course.setCategory(new Category(cmd.category()));
    }

    if (cmd.level() != null) {
      course.setLevel(new Level(cmd.level()));
    }

    if (cmd.description() != null) {
      course.setDescription(cmd.description());
    }

    if (cmd.lessonsCount() != null) {
      course.setLessonsCount(cmd.lessonsCount());
    }

    if (cmd.firstLessonYoutubeLink() != null) {
      course.setFirstLessonYoutubeLink(cmd.firstLessonYoutubeLink());
    }

    if (cmd.skillIds() != null) {
      course.setSkillIds(cmd.skillIds());
      skillIdsChanged =
          !Set.copyOf(previousSkillIds).equals(Set.copyOf(course.getSkillIdsAsString()));
    }

    if (cmd.category() != null || cmd.skillIds() != null) {
      this.coursePolicies.validateCourseSkills(course.getCategory(), course.getSkillIdsAsString());
    }

    Course savedCourse = this.courseRepository.save(course);

    if (skillIdsChanged) {
      this.applicationEventPublisher.publishEvent(
          new CourseSkillIdsChangedEvent(
              savedCourse.getId(), previousSkillIds, savedCourse.getSkillIdsAsString()));
    }

    return savedCourse;
  }
}
