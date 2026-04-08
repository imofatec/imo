package com.imo.backend.contexts.catalog.course.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String>, CustomCourseRepository {
  default Course findByIdOrThrow(String id) {
    return this.findById(id).orElseThrow(() -> new NotFoundException("Curso não Encontrado"));
  }

  default Course findByLessonIdOrThrow(String lessonId) {
    return this.findByLessonId(lessonId)
        .orElseThrow(() -> new NotFoundException("Aula não Encontrada"));
  }
}
