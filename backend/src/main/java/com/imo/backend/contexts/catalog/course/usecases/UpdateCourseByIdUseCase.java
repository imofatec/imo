package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CoursePolicies;
import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.recommendation.events.RecommendationContextChangedEvent;
import java.util.List;
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
    boolean shouldRefreshRecommendations = false;

    if (cmd.name() != null) {
      this.coursePolicies.checkSlugConflict(
          course.getContributorId(), Slug.create(cmd.name()), courseId);

      course.setName(new CourseName(cmd.name()));
    }

    if (cmd.category() != null) {
      course.setCategory(new Category(cmd.category()));
      shouldRefreshRecommendations = true;
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
      shouldRefreshRecommendations = true;
    }

    if (cmd.category() != null || cmd.skillIds() != null) {
      this.coursePolicies.validateCourseSkills(course.getCategory(), course.getSkillIdsAsString());
    }

    Course savedCourse = this.courseRepository.save(course);

    if (shouldRefreshRecommendations) {
      List<String> relatedSkillIds =
          java.util.stream.Stream.concat(
                  previousSkillIds.stream(), savedCourse.getSkillIdsAsString().stream())
              .distinct()
              .toList();
      this.applicationEventPublisher.publishEvent(
          new RecommendationContextChangedEvent(savedCourse.getId(), relatedSkillIds));
    }

    return savedCourse;
  }
}
