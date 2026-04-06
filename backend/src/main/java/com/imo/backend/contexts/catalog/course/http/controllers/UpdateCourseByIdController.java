package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseResponseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.course.usecases.UpdateCourseByIdUseCase;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.lib.token.TokenManager;
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
  private final UpdateCourseByIdUseCase updateCourseByIdService;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  public UpdateCourseByIdController(
      UpdateCourseByIdUseCase updateCourseByIdService,
      TokenManager tokenManager,
      ValidateUserCourseAccessService validateUserCourseAccessService) {
    this.updateCourseByIdService = updateCourseByIdService;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
  }

  @Operation(summary = "Update course fields by id")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/{id}")
  public ResponseEntity<CourseResponseDTO> handle(
      @PathVariable String id, @Valid @RequestBody UpdateCourseByIdRequest dto) {
    MongoDB.validateObjectId(id);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    this.validateUserCourseAccessService.execute(userId, id);

    var updatedCourse = this.updateCourseByIdService.execute(id, dto);

    return updatedCourse == null
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(CourseResponseDTO.fromEntity(updatedCourse));
  }
}
