package com.imo.backend.contexts.identity.repositories;

import com.imo.backend.contexts.identity.User;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

  private final MongoTemplate mongoTemplate;

  CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public User toggleAccessById(String id, Boolean isConfirmed) {
    var query = new Query(Criteria.where("_id").is(id));
    Update update = new Update();
    update.set("isConfirmed", !isConfirmed);
    return this.mongoTemplate.findAndModify(
        query,
        update,
        FindAndModifyOptions.options().returnNew(true),
        User.class
    );
  }
}
