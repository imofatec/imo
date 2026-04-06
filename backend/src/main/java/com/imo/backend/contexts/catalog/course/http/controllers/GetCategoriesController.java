package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CategoryResponseDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCategoriesController extends CourseController {
  private final CourseRepository courseRepository;

  public GetCategoriesController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Get categories")
  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResponseDTO>> handle(
      @Parameter(description = "Page number to retrieve", example = "0", required = false)
          @RequestParam(required = false)
          Integer page,
      @Parameter(description = "Size of each page", example = "10", required = false)
          @RequestParam(required = false)
          Integer size) {

    var categories =
        (page == null || size == null)
            ? this.courseRepository.findAllCategories()
            : this.courseRepository.findAllCategories(page, size);

    var response = categories.stream().map(CategoryResponseDTO::fromVO).toList();

    return ResponseEntity.ok(response);
  }
}
