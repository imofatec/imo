package com.imo.backend.contexts.recognition.repositories;

import com.imo.backend.contexts.recognition.UserAchievement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAchievementsRepository
    extends MongoRepository<UserAchievement, String>, CustomUserAchievementRepository {}
