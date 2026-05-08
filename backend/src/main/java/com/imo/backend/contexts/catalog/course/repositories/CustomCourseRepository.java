package com.imo.backend.contexts.catalog.course.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CourseDetails;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import java.util.List;
import java.util.Optional;

public interface CustomCourseRepository {
  List<Course> findAllByContributorId(String id);

  Optional<Course> findByIdAndIsActive(String id, boolean isActive);

  Optional<Course> findByLessonId(String lessonId);

  List<Course> search(
      CourseSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith,
      boolean isActive);

  long countSearch(
      CourseSearchParams searchParams,
      MatchType matchType,
      CombineWith combineWith,
      boolean isActive);

  List<CourseDetails> searchDetails(
      CourseSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith,
      boolean isActive);

  CourseDetails findCourseDetailsByIdOrThrow(String id, boolean isActive);
}
