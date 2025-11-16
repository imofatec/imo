package com.imo.backend.modules.course.value_objects;

import com.imo.backend.utils.Slug;

public record Category(
    String name,
    String slug
) {
  public Category(String name) {
    this(name, Slug.create(name));
  }
}
