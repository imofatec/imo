package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.value_objects.Category;

public record CategoryDTO(
    String name,
    String slug
) {
  public static CategoryDTO fromVO(Category category) {
    return new CategoryDTO(category.name(), category.slug());
  }
}
