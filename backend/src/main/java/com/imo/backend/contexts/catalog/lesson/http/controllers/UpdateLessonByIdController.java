package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.UpdateLessonInput;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonResponseDTO;
import com.imo.backend.contexts.catalog.lesson.services.UpdateLessonByIdService;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateLessonByIdController extends LessonController {
  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  private final UpdateLessonByIdService updateLessonByIdService;

  private final CourseRepository courseRepository;

  public UpdateLessonByIdController(
      ValidateUserCourseAccessService validateUserCourseAccessService,
      UpdateLessonByIdService updateLessonByIdService,
      CourseRepository courseRepository
  ) {
    this.validateUserCourseAccessService = validateUserCourseAccessService;
    this.updateLessonByIdService = updateLessonByIdService;
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Update lesson by id")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/{id}")
  public ResponseEntity<LessonResponseDTO> handle(
      @PathVariable
      String id,
      @Valid
      @RequestBody
      UpdateLessonInput dto
  ) {
    MongoDB.validateObjectId(id);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var existingCourse = this.courseRepository.findByLessonIdOrThrow(id);
    this.validateUserCourseAccessService.execute(userId, existingCourse.getId());

    var updatedLesson = this.updateLessonByIdService.execute(id, dto);

    return updatedLesson != null
        ? ResponseEntity.ok(LessonResponseDTO.fromEntity(updatedLesson))
        : ResponseEntity.noContent().build();
  }
}
