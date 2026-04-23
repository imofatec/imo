package com.imo.backend.contexts.recognition.repositories;

import com.imo.backend.contexts.recognition.UserAchievement;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class CustomUserAchievementRepositoryImpl implements CustomUserAchievementRepository {
  private final MongoTemplate mongoTemplate;

  public CustomUserAchievementRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<UserAchievement> findAllByUserId(String userId) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("userId").is(new ObjectId(userId)))
            .with(Sort.by(Sort.Direction.DESC, "createdAt"));

    return this.mongoTemplate.find(query, UserAchievement.class);
  }

  @Override
  public Optional<UserAchievement> findByAchievementKeyAndUserId(
      String achievementKey, String userId) {
    Query query =
        new Query()
            .addCriteria(
                Criteria.where("userId")
                    .is(new ObjectId(userId))
                    .and("achievementKey")
                    .is(achievementKey));

    return Optional.ofNullable(this.mongoTemplate.findOne(query, UserAchievement.class));
  }
}
