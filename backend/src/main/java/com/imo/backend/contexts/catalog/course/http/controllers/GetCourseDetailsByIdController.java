package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.exceptions.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCourseDetailsByIdController extends CourseController {

  private final CourseRepository courseRepository;

  public GetCourseDetailsByIdController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Get course details by id (includes lessons)")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Curso encontrado com aulas", content = @Content(schema = @Schema(implementation = CourseDetailsDTO.class))),
      @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/details/{id}")
  public ResponseEntity<CourseDetailsDTO> execute(
      @PathVariable
      String id
  ) {
    var courseDetails = this.courseRepository.findCourseDetailsByIdOrThrow(id);
    return ResponseEntity.ok(CourseDetailsDTO.fromCourseDetails(courseDetails));
  }
}
