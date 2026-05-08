package com.imo.backend.contexts.identity.recovery.repositories;

import com.imo.backend.contexts.identity.recovery.RecoveryCode;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRecoveryCodeRepositoryImpl implements CustomRecoveryCodeRepository {
  private final MongoTemplate mongoTemplate;

  public CustomRecoveryCodeRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<RecoveryCode> findByUserId(String userId) {
    Query query = new Query(Criteria.where("userId").is(new ObjectId(userId)));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    return Optional.ofNullable(this.mongoTemplate.findOne(query, RecoveryCode.class));
  }
}
