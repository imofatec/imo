package com.imo.backend.contexts.identity.achievement.repositories;

import com.imo.backend.contexts.identity.achievement.AchievementCode;
import com.imo.backend.contexts.identity.achievement.UserAchievement;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserAchievementRepository {
  Optional<UserAchievement> findByAchievementCodeAndUserId(AchievementCode code, String userId);
}
