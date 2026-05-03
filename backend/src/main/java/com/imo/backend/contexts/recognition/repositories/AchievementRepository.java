package com.imo.backend.contexts.recognition.repositories;

import com.imo.backend.contexts.recognition.Achievement;
import com.imo.backend.contexts.recognition.AchievementTrigger;
import java.util.List;
import java.util.Optional;

public interface AchievementRepository {
  Optional<Achievement> findByKey(String key);

  List<Achievement> findAllOrderByDisplayOrderAsc();

  List<Achievement> findAllByTriggerOrderByDisplayOrderAsc(AchievementTrigger trigger);
}
