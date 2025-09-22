package com.imo.backend.modules.course.repositories;

import com.imo.backend.utils.MongoDB;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

public class CourseSearchFilter {
  public static Map<String, Object> apply(CourseSearchParams searchParams) {
    Map<String, Object> filters = new HashMap<>();
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
