package com.imo.backend.modules.course.value_objects;

import com.imo.backend.utils.Slug;

public record Level(
    String name,
    String slug
) {
  public Level(String name) {
    this(name, Slug.create(name));
  }
}
