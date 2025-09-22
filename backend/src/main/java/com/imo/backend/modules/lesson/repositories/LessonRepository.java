package com.imo.backend.modules.lesson.repositories;

import com.imo.backend.modules.lesson.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonRepository extends MongoRepository<Lesson, String>, CustomLessonRepository {
}
