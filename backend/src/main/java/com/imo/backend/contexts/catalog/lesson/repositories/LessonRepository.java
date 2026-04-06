package com.imo.backend.contexts.catalog.lesson.repositories;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonRepository extends MongoRepository<Lesson, String>, CustomLessonRepository {

  default Lesson findByIdOrThrow(String id) {
    return this.findById(id).orElseThrow(() -> new NotFoundException("Lição não Encontrada"));
  }
}
