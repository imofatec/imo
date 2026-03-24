package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CategoryDTO;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCategoriesController extends CourseController {
  @Operation(summary = "Get categories")
  @GetMapping("/categories")
  public ResponseEntity<List<CategoryDTO>> handle(
  ) {
    List<Category> categories = Categories.getAll().stream().map(Category::new).toList();

    return ResponseEntity.ok(categories.stream().map(CategoryDTO::fromVO).toList());
  }
}
