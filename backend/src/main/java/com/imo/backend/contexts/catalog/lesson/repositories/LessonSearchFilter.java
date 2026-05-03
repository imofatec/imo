package com.imo.backend.contexts.catalog.lesson.repositories;

import java.util.HashMap;
import java.util.Map;

public class LessonSearchFilter {
  public static Map<String, Object> apply(LessonSearchParams searchParams) {
    Map<String, Object> filters = new HashMap<>();

    if (searchParams.courseName() != null) {
      filters.put("course.name.name", searchParams.courseName());
    }

    if (searchParams.courseNameSlug() != null) {
      filters.put("course.name.slug", searchParams.courseNameSlug());
    }

    return filters;
  }
}
