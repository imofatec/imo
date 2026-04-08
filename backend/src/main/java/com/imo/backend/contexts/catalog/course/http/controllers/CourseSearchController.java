package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.repositories.CourseSearchParams;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSearchController extends CourseController {
  private final CourseRepository courseRepository;

  public CourseSearchController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Search course")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Cursos encontrados",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))))
      })
  @GetMapping("/search")
  public ResponseEntity<List<CourseDTO>> handle(
      @Parameter(description = "Query params to search", example = "slugCategory=dev-web")
          @ParameterObject
          CourseSearchParams courseSearchParams,
      @Parameter(description = "Query type of match", example = "PERFECT")
          @RequestParam(defaultValue = "PERFECT")
          MatchType matchType,
      @Parameter(description = "Query params to search", example = "AND")
          @RequestParam(defaultValue = "AND")
          CombineWith combineWith,
      @Parameter(description = "Page number to retrieve", example = "0", required = false)
          @RequestParam(required = false)
          Integer page,
      @Parameter(description = "Size of each page", example = "10", required = false)
          @RequestParam(required = false)
          Integer size) {
    var courses =
        (page == null || size == null)
            ? this.courseRepository.search(courseSearchParams, matchType, combineWith)
            : this.courseRepository.search(courseSearchParams, page, size, matchType, combineWith);

    var response = courses.stream().map(CourseDTO::fromEntity).toList();

    return ResponseEntity.ok(response);
  }
}
