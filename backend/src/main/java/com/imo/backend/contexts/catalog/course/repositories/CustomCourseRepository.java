package com.imo.backend.contexts.catalog.course.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CourseDetails;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface CustomCourseRepository {
  List<Course> findAllByContributorId(String id);

  List<Course> findAllByContributorId(String id, Pageable page);

  Optional<Course> findByLessonId(String lessonId);

  List<Course> search(
      CourseSearchParams searchParams, MatchType matchType, CombineWith combineWith);

  List<Course> search(
      CourseSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith);

  List<CourseDetails> searchDetails(
      CourseSearchParams searchParams, MatchType matchType, CombineWith combineWith);

  List<CourseDetails> searchDetails(
      CourseSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith);

  CourseDetails findCourseDetailsByIdOrThrow(String id);

  List<Category> findAllCategories();

  List<Category> findAllCategories(int page, int size);
}
