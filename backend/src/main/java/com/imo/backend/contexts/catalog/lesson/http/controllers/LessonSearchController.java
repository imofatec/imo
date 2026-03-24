package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.repositories.CourseSearchParams;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonSearchParams;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LessonSearchController extends LessonController {
  private final CourseRepository courseRepository;

  public LessonSearchController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Search lesson")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Aulas encontradas",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = LessonDTO.class)))),
      @ApiResponse(responseCode = "401", description = "Não autenticado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @GetMapping("/search")
  public ResponseEntity<List<LessonDTO>> handle(
      @Parameter(description = "Query params to search", example = "slugCategory=dev-web")
      @ParameterObject
      LessonSearchParams lessonSearchParams,
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
      Integer size
  ) {

    CourseSearchParams searchParams = new CourseSearchParams(
        lessonSearchParams.courseName(),
        lessonSearchParams.courseNameSlug(),
        null,
        null,
        null
    );

    var courseDetailsStream = (page == null || size == null)
        ? this.courseRepository
        .searchDetails(searchParams, matchType, combineWith)
        .stream()
        : this.courseRepository
            .searchDetails(searchParams, page, size, matchType, combineWith)
            .stream();

    var response = courseDetailsStream
        .flatMap(courseDetails -> courseDetails.lessons().stream())
        .map(LessonDTO::fromEntity)
        .toList();

    return ResponseEntity.ok(response);
  }
}
