package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseResponseDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCourseByIdController extends CourseController {

  private final CourseRepository courseRepository; 
  public GetCourseByIdController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Get course by id")
  @GetMapping("/{id}")
  public ResponseEntity<CourseResponseDTO> execute(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    var course = this.courseRepository.findByIdOrThrow(id);
    return ResponseEntity.ok(CourseResponseDTO.fromEntity(course));
  }
}
