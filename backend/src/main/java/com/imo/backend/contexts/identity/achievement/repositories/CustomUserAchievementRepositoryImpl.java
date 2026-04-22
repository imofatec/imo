package com.imo.backend.contexts.identity.achievement.repositories;

import com.imo.backend.contexts.identity.achievement.AchievementCode;
import com.imo.backend.contexts.identity.achievement.UserAchievement;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class CustomUserAchievementRepositoryImpl implements CustomUserAchievementRepository {
  private final MongoTemplate mongoTemplate;

  public CustomUserAchievementRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<UserAchievement> findByAchievementCodeAndUserId(
      AchievementCode code, String userId) {
    Query query =
        new Query()
            .addCriteria(
                Criteria.where("userId").is(new ObjectId(userId)).and("achievementCode").is(code));

    return Optional.ofNullable(this.mongoTemplate.findOne(query, UserAchievement.class));
  }
}
