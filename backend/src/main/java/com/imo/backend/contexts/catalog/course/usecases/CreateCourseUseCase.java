package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.commands.CreateCourseCommand;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.CreateLessonUseCase;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCourseUseCase {
  private final CreateLessonUseCase createLessonUseCase;

  private final CourseRepository courseRepository;

  public CreateCourseUseCase(
      CreateLessonUseCase createLessonUseCase,
      CourseRepository courseRepository) {
    this.createLessonUseCase = createLessonUseCase;
    this.courseRepository = courseRepository;
  }

  public CourseDetailsDTO execute(CreateCourseCommand cmd, String contributorId) {
    var potentialNewSlugCourse = Slug.create(cmd.name());
    this.checkConflictContributorCourse(contributorId, potentialNewSlugCourse);

    var newCourse = this.courseRepository.save(new Course(true, cmd.contributorId(), cmd.name(), cmd.level(),
        cmd.category(), cmd.description(), cmd.lessons().getFirst().youtubeLink(), cmd.lessons().size()));

    var newLessons = this.createLessonUseCase.execute(cmd.lessons(), newCourse.getId());

    return new CourseDetailsDTO(CourseDTO.fromEntity(newCourse), newLessons);
  }

  private void checkConflictContributorCourse(String contributorId, String potentialNewSlugCourse) {
    var existingContributorCourse = this.courseRepository
        .findAllByContributorId(contributorId)
        .stream()
        .anyMatch(course -> course.getName().slug().equals(potentialNewSlugCourse));

    if (existingContributorCourse) {
      throw new ConflictException(String.format(
          "Você ja cadastrou o curso %s anteriormente",
          potentialNewSlugCourse));
    }
  }
}
