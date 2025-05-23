package com.imo.backend.models.lessons.services;

import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;
import com.imo.backend.models.lessons.services.interfaces.PushLessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushLessonServiceImpl implements PushLessonService {
  private final CourseRepository courseRepository;

  public PushLessonServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public List<NoCommentsLesson> execute(String courseId, List<CreateLessonDto> dto) {
    var updatedCourse = this.courseRepository.pushLesson(courseId, dto);

    if (updatedCourse == null) {
      return null;
    }

    return updatedCourse.getLessons().stream()
        .map(NoCommentsLesson::new)
        .toList();
  }
}
