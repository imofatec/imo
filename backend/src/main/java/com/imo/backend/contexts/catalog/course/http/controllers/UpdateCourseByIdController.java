package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.course.usecases.UpdateCourseByIdUseCase;
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
public class UpdateCourseByIdController extends CourseController {
  private final UpdateCourseByIdUseCase useCase;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  public UpdateCourseByIdController(
      UpdateCourseByIdUseCase useCase,
      ValidateUserCourseAccessService validateUserCourseAccessService
  ) {
    this.useCase = useCase;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
  }

  @Operation(summary = "Update course fields by id")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/{id}")
  public ResponseEntity<CourseDTO> handle(
      @PathVariable
      String id,
      @Valid
      @RequestBody
      UpdateCourseByIdRequest dto
  ) {
    MongoDB.validateObjectId(id);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    this.validateUserCourseAccessService.execute(userId, id);

    var updatedCourse = this.useCase.execute(id, dto.toCommand(id));

    return updatedCourse == null
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(CourseDTO.fromEntity(updatedCourse));
  }
}
