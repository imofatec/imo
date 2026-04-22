package com.imo.backend.contexts.identity.achievement.repositories;

import com.imo.backend.contexts.identity.achievement.UserAchievement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAchievementsRepository
    extends MongoRepository<UserAchievement, String>, CustomUserAchievementRepository {}
