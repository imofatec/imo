package com.imo.backend.contexts.catalog.course.value_objects;

import com.imo.backend.contexts.common.Slug;

public record Level(String name, String slug) {
  public Level(String name) {
    this(name, Slug.create(name));
  }
}
