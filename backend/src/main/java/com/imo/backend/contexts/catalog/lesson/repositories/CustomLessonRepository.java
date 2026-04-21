package com.imo.backend.contexts.catalog.lesson.repositories;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import java.util.List;

public interface CustomLessonRepository {
  List<Lesson> findAllByCourseId(String courseId);

  List<Lesson> search(
      LessonSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith);

  long countSearch(LessonSearchParams searchParams, MatchType matchType, CombineWith combineWith);
}
