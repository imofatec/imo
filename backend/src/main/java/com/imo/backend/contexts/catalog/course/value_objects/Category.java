package com.imo.backend.contexts.catalog.course.value_objects;

import com.imo.backend.contexts.common.Slug;

public record Category(Categories name, String slug) {
  public Category(Categories name) {
    this(name, Slug.create(name.getValue()));
  }
}
