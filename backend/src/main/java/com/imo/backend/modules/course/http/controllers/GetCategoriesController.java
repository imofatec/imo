package com.imo.backend.modules.course.http.controllers;

import com.imo.backend.modules.course.repositories.CourseRepository;
import com.imo.backend.modules.course.value_objects.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCategoriesController extends CourseController {
  private final CourseRepository courseRepository;

  public GetCategoriesController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Get categories")
  @GetMapping("/categories")
  public ResponseEntity<List<Category>> handle(
      @Parameter(description = "Page number to retrieve", example = "0", required = false)
      @RequestParam(required = false)
      Integer page,
      @Parameter(description = "Size of each page", example = "10", required = false)
      @RequestParam(required = false)
      Integer size
  ) {
    return ResponseEntity.ok((page == null || size == null)
        ? this.courseRepository.findAllCategories()
        : this.courseRepository.findAllCategories(page, size));
  }
}
