package com.imo.backend.contexts.catalog.lesson.repositories;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomLessonRepositoryImpl implements CustomLessonRepository {
  private final MongoTemplate mongoTemplate;

  public CustomLessonRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Lesson> findAllByCourseId(String courseId) {
    Query query = new Query(Criteria.where("courseId").is(new ObjectId(courseId)));
    return this.mongoTemplate.find(query, Lesson.class);
  }
}
