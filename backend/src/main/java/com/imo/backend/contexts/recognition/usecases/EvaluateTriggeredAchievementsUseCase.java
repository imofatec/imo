package com.imo.backend.contexts.recognition.usecases;

import com.imo.backend.contexts.recognition.Achievement;
import com.imo.backend.contexts.recognition.AchievementPolicies;
import com.imo.backend.contexts.recognition.AchievementTrigger;
import com.imo.backend.contexts.recognition.UserAchievement;
import com.imo.backend.contexts.recognition.events.AchievementUnlockedEvent;
import com.imo.backend.contexts.recognition.repositories.AchievementRepository;
import com.imo.backend.contexts.recognition.repositories.UserAchievementsRepository;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EvaluateTriggeredAchievementsUseCase {
  private final AchievementRepository achievementRepository;

  private final AchievementPolicies achievementPolicies;

  private final UserAchievementsRepository userAchievementsRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public EvaluateTriggeredAchievementsUseCase(
      AchievementRepository achievementRepository,
      AchievementPolicies achievementPolicies,
      UserAchievementsRepository userAchievementsRepository,
      ApplicationEventPublisher applicationEventPublisher) {
    this.achievementRepository = achievementRepository;
    this.achievementPolicies = achievementPolicies;
    this.userAchievementsRepository = userAchievementsRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void evaluateByCourseFinished(String userId) {
    this.evaluate(userId, AchievementTrigger.COURSE_FINISHED);
  }

  public void evaluateByLessonWatched(String userId) {
    this.evaluate(userId, AchievementTrigger.LESSON_WATCHED);
  }

  private void evaluate(String userId, AchievementTrigger trigger) {
    List<Achievement> achievements =
        this.achievementRepository.findAllByTriggerOrderByDisplayOrderAsc(trigger);

    for (Achievement achievement : achievements) {
      if (!this.achievementPolicies.canUnlockAchievement(userId, achievement)) {
        continue;
      }

      this.userAchievementsRepository.save(new UserAchievement(userId, achievement.getKey()));
      this.applicationEventPublisher.publishEvent(
          new AchievementUnlockedEvent(userId, achievement));
    }
  }
}
