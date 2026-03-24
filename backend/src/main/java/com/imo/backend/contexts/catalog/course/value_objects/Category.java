package com.imo.backend.contexts.catalog.course.value_objects;

import com.imo.backend.contexts.common.Slug;

public record Category(
    String name,
    String slug
) {
  public Category(Categories name) {
    this(name.getValue(), Slug.create(name.getValue()));
  }
}
