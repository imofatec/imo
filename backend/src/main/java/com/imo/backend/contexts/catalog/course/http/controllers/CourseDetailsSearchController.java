package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
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
public class CourseDetailsSearchController extends CourseController {

  private final CourseRepository courseRepository;

  public CourseDetailsSearchController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Search course details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Detalhes dos cursos encontrados",
            content =
                @Content(
                    array =
                        @ArraySchema(schema = @Schema(implementation = CourseDetailsDTO.class))))
      })
  @GetMapping("/search/details")
  public ResponseEntity<List<CourseDetailsDTO>> handle(
      @Parameter(description = "Query params to search", example = "slugCategory=dev-web")
          @ParameterObject
          CourseSearchParams courseSearchParams,
      @Parameter(description = "Filter by active status", example = "true")
          @RequestParam(defaultValue = "true")
          boolean active,
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

    var details =
        (page == null || size == null)
            ? this.courseRepository.searchDetails(
                courseSearchParams, matchType, combineWith, active)
            : this.courseRepository.searchDetails(
                courseSearchParams, page, size, matchType, combineWith, active);

    var response = details.stream().map(CourseDetailsDTO::fromCourseDetails).toList();

    return ResponseEntity.ok(response);
  }
}
