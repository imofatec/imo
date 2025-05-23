package com.imo.backend.models.course.services.get.courses;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.services.get.courses.interfaces.GetCourseByLessonIdService;
import org.springframework.stereotype.Service;

@Service
public class GetCourseByLessonIdServiceImpl implements GetCourseByLessonIdService {
  private final CourseRepository courseRepository;

  public GetCourseByLessonIdServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(String lessonId) {
    var foundCourse = this.courseRepository.findByLessonId(lessonId);

    if (foundCourse == null) {
      throw new NotFoundException("Aula não encontrada");
    }

    return foundCourse;
  }
}
