package com.imo.backend.models.course.services.authorization;

import com.imo.backend.exceptions.custom.ForbiddenException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.services.authorization.interfaces.ValidateUserCourseAccessService;
import org.springframework.stereotype.Service;

@Service
public class ValidateUserCourseAccessServiceImpl implements ValidateUserCourseAccessService {
  private final CourseRepository courseRepository;

  public ValidateUserCourseAccessServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public void execute(String userId, String courseId) {
    var course = courseRepository.findById(courseId)
        .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

    if (!course.getContributorId().equals(userId)) {
      throw new ForbiddenException("Usuário sem permissão para atualizar este curso");
    }
  }
}
