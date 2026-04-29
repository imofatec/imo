package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CoursePolicies;
import com.imo.backend.contexts.catalog.course.commands.CreateCourseCommand;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.lesson.usecases.CreateLessonUseCase;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.learning_path.recommendation.events.RecommendationContextChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CreateCourseUseCase {
  private final CreateLessonUseCase createLessonUseCase;

  private final CourseRepository courseRepository;

  private final CoursePolicies coursePolicies;

  private final UserRepository userRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public CreateCourseUseCase(
      CreateLessonUseCase createLessonUseCase,
      CourseRepository courseRepository,
      CoursePolicies coursePolicies,
      UserRepository userRepository,
      ApplicationEventPublisher applicationEventPublisher) {
    this.createLessonUseCase = createLessonUseCase;
    this.courseRepository = courseRepository;
    this.coursePolicies = coursePolicies;
    this.userRepository = userRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public CourseDetailsDTO execute(CreateCourseCommand cmd) {
    this.userRepository.findByIdOrThrow(cmd.contributorId()).assertCanContributeCourse();

    var potentialNewSlugCourse = Slug.create(cmd.name());
    this.coursePolicies.checkSlugConflict(cmd.contributorId(), potentialNewSlugCourse, null);

    var newCourse =
        new Course(
            true,
            cmd.contributorId(),
            cmd.name(),
            cmd.level(),
            cmd.category(),
            cmd.description(),
            cmd.lessons().getFirst().youtubeLink(),
            cmd.lessons().size(),
            cmd.skillIds());

    this.coursePolicies.validateCourseSkills(
        new Category(cmd.category()), newCourse.getSkillIdsAsString());

    newCourse = this.courseRepository.save(newCourse);

    var newLessons = this.createLessonUseCase.execute(cmd.lessons(), newCourse.getId());
    this.applicationEventPublisher.publishEvent(
        new RecommendationContextChangedEvent(newCourse.getId(), newCourse.getSkillIdsAsString()));

    return new CourseDetailsDTO(CourseDTO.fromEntity(newCourse), newLessons);
  }
}
