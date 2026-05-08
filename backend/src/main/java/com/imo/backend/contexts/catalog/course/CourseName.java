package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.common.Slug;

public record CourseName(String name, String slug) {
  public CourseName(String name) {
    this(name, Slug.create(name));
  }
}
