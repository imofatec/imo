package com.imo.backend.contexts.catalog.course.http.middlewares.impl;

import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import org.springframework.stereotype.Service;

@Service
public class ValidateUserCourseAccessServiceImpl implements ValidateUserCourseAccessService {
  private final CourseRepository courseRepository;

  public ValidateUserCourseAccessServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public void execute(String userId, String courseId) {
    var course = courseRepository.findByIdOrThrow(courseId);

    if (!course.getContributorId().equals(userId)) {
      throw new ForbiddenException("Usuário sem permissão para atualizar este curso");
    }
  }
}
