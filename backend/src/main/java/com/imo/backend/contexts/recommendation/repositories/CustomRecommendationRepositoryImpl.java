package com.imo.backend.contexts.recommendation.repositories;

import com.imo.backend.contexts.recommendation.Recommendation;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CustomRecommendationRepositoryImpl implements CustomRecommendationRepository {
  private final MongoTemplate mongoTemplate;

  public CustomRecommendationRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Recommendation upsertByUserId(String userId, List<String> courseIds) {
    LocalDateTime now = LocalDateTime.now();
    Query query = new Query().addCriteria(Criteria.where("userId").is(new ObjectId(userId)));
    Update update =
        new Update()
            .set("userId", new ObjectId(userId))
            .set("courseIds", courseIds.stream().map(ObjectId::new).toList())
            .set("updatedAt", now)
            .setOnInsert("createdAt", now);

    return this.mongoTemplate.findAndModify(
        query,
        update,
        FindAndModifyOptions.options().returnNew(true).upsert(true),
        Recommendation.class);
  }
}
