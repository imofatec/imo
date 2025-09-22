package com.imo.backend.modules.comment.repositories;

import com.imo.backend.modules.comment.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {
  private final MongoTemplate mongoTemplate;

  public CustomCommentRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Comment> findAllByLessonId(String lessonId) {
    Query query = new Query().addCriteria(Criteria.where("lessonId").is(new ObjectId(lessonId)));
    return this.mongoTemplate.find(query, Comment.class);
  }

  @Override
  public List<Comment> findAllByLessonId(String lessonId, Pageable pageable) {
    Query query = new Query()
        .addCriteria(Criteria.where("lessonId").is(new ObjectId(lessonId)))
        .with(pageable);
    return this.mongoTemplate.find(query, Comment.class);
  }
}
