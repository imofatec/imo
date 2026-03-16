package com.imo.backend.contexts.catalog.course.orchestrators;

import com.imo.backend.contexts.catalog.course.actions.CreateCourseAction;
import com.imo.backend.contexts.catalog.course.actions.inputs.CreateCourseInput;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseResponseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.services.CreateLessonService;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCourseOrchestrator {
  private final CreateCourseAction createCourseAction;

  private final CreateLessonService createLessonService;

  private final CourseRepository courseRepository;

  public CreateCourseOrchestrator(
      CreateCourseAction createCourseAction,
      CreateLessonService createLessonService,
      CourseRepository courseRepository
  ) {
    this.createCourseAction = createCourseAction;
    this.createLessonService = createLessonService;
    this.courseRepository = courseRepository;
  }

  public CourseDetailsDTO execute(CreateCourseRequest createCourseRequest, String contributorId) {
    var potentialNewSlugCourse = Slug.create(createCourseRequest.name());
    this.checkConflictContributorCourse(contributorId, potentialNewSlugCourse);

    var newCourse = this.createCourseAction.execute(new CreateCourseInput(
        createCourseRequest.name(),
        createCourseRequest.category(),
        createCourseRequest.level(),
        createCourseRequest.description(),
        createCourseRequest.lessons(),
        contributorId
    ));

    var newLessons = this.createLessonService.execute(
        createCourseRequest.lessons(),
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
