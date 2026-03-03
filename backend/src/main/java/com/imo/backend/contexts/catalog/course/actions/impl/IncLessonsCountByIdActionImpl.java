package com.imo.backend.contexts.catalog.course.actions.impl;

import com.imo.backend.contexts.catalog.course.actions.IncLessonsCountByIdAction;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class IncLessonsCountByIdActionImpl implements IncLessonsCountByIdAction {
  private final CourseRepository courseRepository;

  public IncLessonsCountByIdActionImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public void execute(String id) {
    this.courseRepository.incLessonsCountById(id);
  }
}
