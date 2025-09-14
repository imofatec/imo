package com.imo.backend.modules.lesson.http.controllers;

import com.imo.backend.modules.course.repositories.CourseRepository;
import com.imo.backend.modules.course.repositories.CourseSearchParams;
import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.repositories.LessonSearchParams;
import com.imo.backend.utils.CombineWith;
import com.imo.backend.utils.MatchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
  @GetMapping("/search")
  public ResponseEntity<List<Lesson>> handle(
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
    List<Lesson> lessons = (page == null || size == null)
        ? this.courseRepository.searchDetails(
        new CourseSearchParams(lessonSearchParams.courseNameSlug(), null, null, null),
        matchType,
        combineWith
    ).stream().flatMap(courseDetails -> courseDetails.lessons().stream()).toList()
        : this.courseRepository.searchDetails(
            new CourseSearchParams(lessonSearchParams.courseNameSlug(), null, null, null),
            page,
            size,
            matchType,
            combineWith
        ).stream().flatMap(courseDetails -> courseDetails.lessons().stream()).toList();
    
    return ResponseEntity.ok(lessons);
  }
}
