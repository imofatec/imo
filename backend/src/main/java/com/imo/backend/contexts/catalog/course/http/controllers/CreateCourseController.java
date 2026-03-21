package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.course.orchestrators.CreateCourseOrchestrator;
import com.imo.backend.contexts.identity.lib.TokenManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCourseController extends CourseController {

  private final CreateCourseOrchestrator createCourseOrchestrator;

  public CreateCourseController(
      TokenManager tokenManager,
      CreateCourseOrchestrator createCourseOrchestrator
  ) {
    this.createCourseOrchestrator = createCourseOrchestrator;
  }

  @Operation(summary = "Create a course")
  @SecurityRequirement(name = "Authorization")
  @PostMapping()
  public ResponseEntity<CourseDetailsDTO> handle(
      @Valid
      @RequestBody
      CreateCourseRequest createCourseRequest
  ) {
    String contributorId = SecurityContextHolder.getContext().getAuthentication().getName();
    var newCourseWithLessons = this.createCourseOrchestrator.execute(
        createCourseRequest,
        contributorId
    );

    return new ResponseEntity<>(newCourseWithLessons, HttpStatus.CREATED);
  }
}
