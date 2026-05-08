package com.imo.backend.contexts.recognition.repositories;

import com.imo.backend.contexts.recognition.UserAchievement;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserAchievementRepository {
  List<UserAchievement> findAllByUserId(String userId);

  Optional<UserAchievement> findByAchievementKeyAndUserId(String achievementKey, String userId);
}
