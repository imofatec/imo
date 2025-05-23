package com.imo.backend.models.lessons.services;

import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.FieldsToUpdateLesson;
import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;
import com.imo.backend.models.lessons.services.interfaces.UpdateLessonByIdService;
import org.springframework.stereotype.Service;

@Service
public class UpdateLessonByIdServiceImpl implements UpdateLessonByIdService {
  private final CourseRepository courseRepository;

  public UpdateLessonByIdServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public NoCommentsLesson execute(String lessonId, FieldsToUpdateLesson fieldsToUpdateLesson) {
    if (fieldsToUpdateLesson.getYoutubeLink() != null) {
      Lesson lessonEntity = new Lesson();
      lessonEntity.setYoutubeLink(fieldsToUpdateLesson.getYoutubeLink());
      fieldsToUpdateLesson.setYoutubeLink(lessonEntity.getYoutubeLink());
    }

    var updatedCourse = this.courseRepository.updateLessonById(lessonId, fieldsToUpdateLesson);

    if (updatedCourse == null) {
      return null;
    }

    var updatedLesson = updatedCourse.getLessons().stream()
        .filter(lesson -> lesson.getId().equals(lessonId)).findFirst()
        .get();

    return new NoCommentsLesson(updatedLesson);
  }
}
