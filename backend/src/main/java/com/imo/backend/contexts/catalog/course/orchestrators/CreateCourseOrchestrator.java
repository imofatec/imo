package com.imo.backend.contexts.catalog.course.orchestrators;

import com.imo.backend.contexts.catalog.course.actions.CreateCourseAction;
import com.imo.backend.contexts.catalog.course.actions.commands.CreateCourseCommand;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseResponseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.CreateLessonUseCase;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCourseOrchestrator {
  private final CreateCourseAction createCourseAction;

  private final CreateLessonUseCase createLessonService;

  private final CourseRepository courseRepository;

  public CreateCourseOrchestrator(
      CreateCourseAction createCourseAction,
      CreateLessonUseCase createLessonService,
      CourseRepository courseRepository
  ) {
    this.createCourseAction = createCourseAction;
    this.createLessonService = createLessonService;
    this.courseRepository = courseRepository;
  }

  public CourseDetailsDTO execute(CreateCourseRequest createCourseRequest, String contributorId) {
    var potentialNewSlugCourse = Slug.create(createCourseRequest.name());
    this.checkConflictContributorCourse(contributorId, potentialNewSlugCourse);

    CreateCourseCommand courseCommand = createCourseRequest.toCommand(contributorId);

    var newCourse = this.createCourseAction.execute(courseCommand);

    var newLessons = this.createLessonService.execute(
          courseCommand.lessons(),
          newCourse.getId()
    );

    return new CourseDetailsDTO(
      CourseResponseDTO.fromEntity(newCourse), 
      newLessons
    );
  }

  private void checkConflictContributorCourse(String contributorId, String potentialNewSlugCourse) {
    var existingContributorCourse = this.courseRepository.findAllByContributorId(contributorId)
        .stream()
        .anyMatch(course -> course.getName().slug().equals(potentialNewSlugCourse));

    if (existingContributorCourse) {
      throw new ConflictException(String.format(
          "Você ja cadastrou o curso %s anteriormente",
          potentialNewSlugCourse
      ));
    }
  }
}
