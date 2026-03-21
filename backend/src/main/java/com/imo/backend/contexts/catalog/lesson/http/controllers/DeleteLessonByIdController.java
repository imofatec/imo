package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonResponseDTO;
import com.imo.backend.contexts.catalog.lesson.usecases.DeleteLessonByIdUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteLessonByIdController extends LessonController {

  private final DeleteLessonByIdUseCase deleteLessonByIdService;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  private final CourseRepository courseRepository;

  public DeleteLessonByIdController(
      DeleteLessonByIdUseCase deleteLessonByIdService,
      ValidateUserCourseAccessService validateUserCourseAccessService,
      CourseRepository courseRepository
  ) {
    this.deleteLessonByIdService = deleteLessonByIdService;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Delete lesson by id")
  @SecurityRequirement(name = "Authorization")
  @DeleteMapping("/{id}")
  public ResponseEntity<LessonResponseDTO> handle(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    var existingCourse = this.courseRepository.findByLessonIdOrThrow(id);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    this.validateUserCourseAccessService.execute(userId, existingCourse.getId());

    var deletedLesson = this.deleteLessonByIdService.execute(id);

    return deletedLesson != null
        ? ResponseEntity.ok(LessonResponseDTO.fromEntity(deletedLesson))
        : ResponseEntity.noContent().build();
  }

}

