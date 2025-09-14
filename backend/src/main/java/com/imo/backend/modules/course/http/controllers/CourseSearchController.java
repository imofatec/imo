package com.imo.backend.modules.course.http.controllers;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.repositories.CourseSearchParams;
import com.imo.backend.modules.course.repositories.CourseRepository;
import com.imo.backend.utils.CombineWith;
import com.imo.backend.utils.MatchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseSearchController extends CourseController {
  private final CourseRepository courseRepository;

  public CourseSearchController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Search course")
  @GetMapping("/search")
  public ResponseEntity<List<Course>> handle(
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
      Integer size
  ) {
    var dto = (page == null || size == null)
        ? this.courseRepository.search(courseSearchParams, matchType, combineWith)
        : this.courseRepository.search(courseSearchParams, page, size, matchType, combineWith);

    return ResponseEntity.ok(dto);
  }
}
