package com.imo.backend.contexts.catalog.course.repositories;

import com.imo.backend.contexts.common.MongoDB;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;

public class CourseSearchFilter {
  public static Map<String, Object> apply(CourseSearchParams searchParams) {
    Map<String, Object> filters = new HashMap<>();
    if (searchParams.name() != null) {
      filters.put("name.name", searchParams.name());
    }

    if (searchParams.nameSlug() != null) {
      filters.put("name.slug", searchParams.nameSlug());
    }

    if (searchParams.categorySlug() != null) {
      filters.put("category.slug", searchParams.categorySlug());
    }

    if (searchParams.levelSlug() != null) {
      filters.put("level.slug", searchParams.levelSlug());
    }

    if (searchParams.contributorId() != null) {
      MongoDB.validateObjectId(searchParams.contributorId());
      filters.put("contributorId", new ObjectId(searchParams.contributorId()));
    }

    return filters;
  }
}
