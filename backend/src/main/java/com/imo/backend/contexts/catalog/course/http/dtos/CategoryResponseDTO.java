package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.course.value_objects.Category;

public record CategoryResponseDTO (
    Categories name,
    String slug
) {
    public static CategoryResponseDTO fromVO(Category category) {
        return new CategoryResponseDTO(
            category.name(),
            category.slug()
        );
    }

}
