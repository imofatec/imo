package com.imo.backend.contexts.catalog.lesson.repositories;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonRepository extends MongoRepository<Lesson, String>, CustomLessonRepository {
}
