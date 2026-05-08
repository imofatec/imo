package com.imo.backend.contexts.recognition.usecases;

import com.imo.backend.contexts.recognition.Achievement;
import com.imo.backend.contexts.recognition.AchievementMetricsResolver;
import com.imo.backend.contexts.recognition.AchievementTrigger;
import com.imo.backend.contexts.recognition.UserAchievement;
import com.imo.backend.contexts.recognition.http.dtos.AchievementCardDTO;
import com.imo.backend.contexts.recognition.http.dtos.ProfileAchievementsDTO;
import com.imo.backend.contexts.recognition.repositories.AchievementRepository;
import com.imo.backend.contexts.recognition.repositories.UserAchievementsRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GetProfileAchievementsUseCase {
  private final AchievementRepository achievementRepository;
  private final UserAchievementsRepository userAchievementsRepository;
  private final AchievementMetricsResolver achievementMetricsResolver;

  public GetProfileAchievementsUseCase(
      AchievementRepository achievementRepository,
      UserAchievementsRepository userAchievementsRepository,
      AchievementMetricsResolver achievementMetricsResolver) {
    this.achievementRepository = achievementRepository;
    this.userAchievementsRepository = userAchievementsRepository;
    this.achievementMetricsResolver = achievementMetricsResolver;
  }

  public ProfileAchievementsDTO execute(String userId) {
    Map<String, UserAchievement> unlockedAchievementsByKey =
        this.indexUnlockedAchievements(this.userAchievementsRepository.findAllByUserId(userId));
    Map<AchievementTrigger, Long> metricsByTrigger =
        this.achievementMetricsResolver.resolveAll(userId);

    List<AchievementCardDTO> items = this.buildItems(unlockedAchievementsByKey, metricsByTrigger);
    int unlockedCount = unlockedAchievementsByKey.size();

    return new ProfileAchievementsDTO(
        items.size(), unlockedCount, items.size() - unlockedCount, items);
  }

  private Map<String, UserAchievement> indexUnlockedAchievements(
      List<UserAchievement> unlockedAchievements) {
    return unlockedAchievements.stream()
        .collect(
            Collectors.toMap(
                achievement -> achievement.getAchievementKey(),
                Function.identity(),
                (left, right) -> left));
  }

  private List<AchievementCardDTO> buildItems(
      Map<String, UserAchievement> unlockedAchievementsByKey,
      Map<AchievementTrigger, Long> metricsByTrigger) {
    return this.achievementRepository.findAllOrderByDisplayOrderAsc().stream()
        .map(
            achievement ->
                this.toAchievementCard(
                    achievement,
                    unlockedAchievementsByKey.get(achievement.getKey()),
                    metricsByTrigger.getOrDefault(achievement.getTrigger(), 0L)))
        .toList();
  }

  private AchievementCardDTO toAchievementCard(
      Achievement achievement, UserAchievement unlockedAchievement, long currentValue) {
    LocalDateTime unlockedAt =
        unlockedAchievement == null ? null : unlockedAchievement.getCreatedAt();
    long displayedCurrentValue =
        this.resolveDisplayedCurrentValue(achievement, unlockedAchievement, currentValue);

    return new AchievementCardDTO(
        achievement.getTrigger(),
        achievement.getKey(),
        achievement.getTitle(),
        achievement.getDescription(),
        this.resolveImageUrl(achievement, unlockedAchievement != null),
        unlockedAt,
        displayedCurrentValue,
        achievement.getTarget(),
        this.resolveProgressPercentage(displayedCurrentValue, achievement.getTarget()));
  }

  private long resolveDisplayedCurrentValue(
      Achievement achievement, UserAchievement unlockedAchievement, long currentValue) {
    if (unlockedAchievement == null) {
      return currentValue;
    }

    return Math.max(currentValue, achievement.getTarget());
  }

  private String resolveImageUrl(Achievement achievement, boolean unlocked) {
    return unlocked ? achievement.getUnlockedImagePath() : achievement.getLockedImagePath();
  }

  private int resolveProgressPercentage(long currentValue, int targetValue) {
    if (targetValue <= 0) {
      return 0;
    }

    return (int) Math.min(100, (currentValue * 100) / targetValue);
  }
}
