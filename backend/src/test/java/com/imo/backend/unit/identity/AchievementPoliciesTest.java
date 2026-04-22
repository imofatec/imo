package com.imo.backend.unit.identity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.imo.backend.contexts.identity.achievement.*;
import com.imo.backend.contexts.identity.achievement.gateways.AchievementMetricsGateway;
import com.imo.backend.contexts.identity.achievement.repositories.UserAchievementsRepository;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AchievementPoliciesTest {
  @Mock private AchievementMetricsGateway achievementMetricsGateway;

  @Mock private UserAchievementsRepository userAchievementsRepository;

  @InjectMocks private AchievementPolicies achievementPolicies;

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de primeiro curso")
  void shouldReturnTrueWhenUserCanUnlockFirstCourseAchievement() {
    String userId = new ObjectId().toString();
    AchievementDefinition achievement = AchievementCatalog.findByCode(AchievementCode.FIRST_COURSE);

    when(this.userAchievementsRepository.findByAchievementCodeAndUserId(achievement.code(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsGateway.countFinishedCoursesByUserId(userId)).thenReturn(1L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsGateway).countFinishedCoursesByUserId(userId);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 10 cursos")
  void shouldReturnTrueWhenUserCanUnlockTenCoursesAchievement() {
    String userId = new ObjectId().toString();
    AchievementDefinition achievement = AchievementCatalog.findByCode(AchievementCode.TEN_COURSES);

    when(this.userAchievementsRepository.findByAchievementCodeAndUserId(achievement.code(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsGateway.countFinishedCoursesByUserId(userId)).thenReturn(10L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsGateway).countFinishedCoursesByUserId(userId);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 10 aulas")
  void shouldReturnTrueWhenUserCanUnlockTenLessonsAchievement() {
    String userId = new ObjectId().toString();
    AchievementDefinition achievement = AchievementCatalog.findByCode(AchievementCode.TEN_LESSONS);

    when(this.userAchievementsRepository.findByAchievementCodeAndUserId(achievement.code(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsGateway.countWatchedLessonsByUserId(userId)).thenReturn(10L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsGateway).countWatchedLessonsByUserId(userId);
  }

  @Test
  @DisplayName("edge (canUnlockAchievement): retorna false quando achievement já foi desbloqueado")
  void shouldReturnFalseWhenAchievementWasAlreadyGranted() {
    String userId = new ObjectId().toString();
    AchievementDefinition achievement = AchievementCatalog.findByCode(AchievementCode.TEN_LESSONS);

    when(this.userAchievementsRepository.findByAchievementCodeAndUserId(
            AchievementCode.TEN_LESSONS, userId))
        .thenReturn(Optional.of(new UserAchievement(userId, AchievementCode.TEN_LESSONS)));

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertFalse(canUnlock);
    verify(this.achievementMetricsGateway, never()).countWatchedLessonsByUserId(userId);
  }
}
