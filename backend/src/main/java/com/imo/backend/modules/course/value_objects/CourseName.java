package com.imo.backend.modules.course.value_objects;

import com.imo.backend.utils.Slug;

public record CourseName(
    String name,
    String slug
) {
  public CourseName(String name) {
    this(name, Slug.create(name));
  }
}
