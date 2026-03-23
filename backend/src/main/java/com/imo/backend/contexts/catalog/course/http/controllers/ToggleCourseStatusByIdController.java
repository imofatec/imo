package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.course.usecases.ToggleCourseStatusByIdUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToggleCourseStatusByIdController extends CourseController {

  private final ToggleCourseStatusByIdUseCase useCase;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  public ToggleCourseStatusByIdController(
      ToggleCourseStatusByIdUseCase useCase,
      ValidateUserCourseAccessService validateUserCourseAccessService
  ) {
    this.useCase = useCase;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
  }

  @Operation(summary = "Toggle course status")
  @SecurityRequirement(name = "Authorization")
  @PatchMapping("/{id}")
  public ResponseEntity<CourseDTO> handle(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    this.validateUserCourseAccessService.execute(userId, id);

    var updatedCourse = useCase.execute(id);

    return updatedCourse != null
        ? ResponseEntity.ok(CourseDTO.fromEntity(updatedCourse))
        : ResponseEntity.noContent().build();
  }
}