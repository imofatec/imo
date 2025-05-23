package com.imo.backend.models.course.services.update;

import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;
import com.imo.backend.models.course.services.update.interfaces.UpdateCourseByIdService;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdServiceImpl implements UpdateCourseByIdService {
  private final CourseRepository courseRepository;

  public UpdateCourseByIdServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public CourseOverview execute(String courseId, FieldsToUpdateCourse fieldsToUpdateCourse) {
    var updatedCourse = this.courseRepository.updateCourseById(courseId, fieldsToUpdateCourse);

    return updatedCourse != null
        ? CourseFactory.createCourseOverview(updatedCourse)
        : null;
  }
}
