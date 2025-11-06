package com.imo.backend.modules.course.value_objects;

import com.imo.backend.utils.Slug;

public record Category(
    Categories name,
    String slug
) {
  public Category(Categories name) {
    this(name, Slug.create(name.getValue()));
  }
}
