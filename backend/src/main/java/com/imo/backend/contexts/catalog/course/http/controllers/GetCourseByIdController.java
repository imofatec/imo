package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Curso encontrado",
          content = @Content(schema = @Schema(implementation = CourseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Curso não encontrado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<CourseDTO> execute(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    var course = this.courseRepository.findByIdOrThrow(id);
    return ResponseEntity.ok(CourseDTO.fromEntity(course));
  }
}
