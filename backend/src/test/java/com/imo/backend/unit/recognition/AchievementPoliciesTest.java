package com.imo.backend.unit.recognition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.imo.backend.contexts.recognition.*;
import com.imo.backend.contexts.recognition.repositories.StaticAchievementRepository;
import com.imo.backend.contexts.recognition.repositories.StaticAchievementRepository.StaticAchievementsKeys;
import com.imo.backend.contexts.recognition.repositories.UserAchievementsRepository;
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
  @Mock private AchievementMetricsResolver achievementMetricsResolver;

  @Mock private UserAchievementsRepository userAchievementsRepository;

  @InjectMocks private AchievementPolicies achievementPolicies;

  private final StaticAchievementRepository staticAchievementRepository =
      new StaticAchievementRepository();

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de primeira aula")
  void shouldReturnTrueWhenUserCanUnlockFirstLessonAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.FIRST_LESSON);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.FIRST_LESSON.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.LESSON_WATCHED))
        .thenReturn(1L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.LESSON_WATCHED);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de primeiro curso")
  void shouldReturnTrueWhenUserCanUnlockFirstCourseAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.FIRST_COURSE);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.FIRST_COURSE.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.COURSE_FINISHED))
        .thenReturn(1L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.COURSE_FINISHED);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 3 cursos")
  void shouldReturnTrueWhenUserCanUnlockThreeCoursesAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.THREE_COURSES);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.THREE_COURSES.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.COURSE_FINISHED))
        .thenReturn(3L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.COURSE_FINISHED);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 5 cursos")
  void shouldReturnTrueWhenUserCanUnlockFiveCoursesAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.FIVE_COURSES);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.FIVE_COURSES.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.COURSE_FINISHED))
        .thenReturn(5L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.COURSE_FINISHED);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 10 cursos")
  void shouldReturnTrueWhenUserCanUnlockTenCoursesAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.TEN_COURSES);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.TEN_COURSES.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.COURSE_FINISHED))
        .thenReturn(10L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.COURSE_FINISHED);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 10 aulas")
  void shouldReturnTrueWhenUserCanUnlockTenLessonsAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.TEN_LESSONS);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.TEN_LESSONS.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.LESSON_WATCHED))
        .thenReturn(10L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.LESSON_WATCHED);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 50 aulas")
  void shouldReturnTrueWhenUserCanUnlockFiftyLessonsAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.FIFTY_LESSONS);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.FIFTY_LESSONS.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.LESSON_WATCHED))
        .thenReturn(50L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.LESSON_WATCHED);
  }

  @Test
  @DisplayName(
      "happy path (canUnlockAchievement): retorna true quando usuário pode desbloquear achievement de 100 aulas")
  void shouldReturnTrueWhenUserCanUnlockHundredLessonsAchievement() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.HUNDRED_LESSONS);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.HUNDRED_LESSONS.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.LESSON_WATCHED))
        .thenReturn(100L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertTrue(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.LESSON_WATCHED);
  }

  @Test
  @DisplayName("edge (canUnlockAchievement): retorna false quando usuário ainda não atingiu a meta")
  void shouldReturnFalseWhenUserDidNotReachTargetYet() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.FIVE_COURSES);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.FIVE_COURSES.name(), userId))
        .thenReturn(Optional.empty());
    when(this.achievementMetricsResolver.resolve(userId, AchievementTrigger.COURSE_FINISHED))
        .thenReturn(4L);

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertFalse(canUnlock);
    verify(this.achievementMetricsResolver).resolve(userId, AchievementTrigger.COURSE_FINISHED);
  }

  @Test
  @DisplayName("edge (canUnlockAchievement): retorna false quando achievement já foi desbloqueado")
  void shouldReturnFalseWhenAchievementWasAlreadyGranted() {
    String userId = new ObjectId().toString();
    Achievement achievement = this.findAchievement(StaticAchievementsKeys.TEN_LESSONS);

    when(this.userAchievementsRepository.findByAchievementKeyAndUserId(
            StaticAchievementsKeys.TEN_LESSONS.name(), userId))
        .thenReturn(
            Optional.of(new UserAchievement(userId, StaticAchievementsKeys.TEN_LESSONS.name())));

    boolean canUnlock = this.achievementPolicies.canUnlockAchievement(userId, achievement);

    assertFalse(canUnlock);
    verify(this.achievementMetricsResolver, never())
        .resolve(userId, AchievementTrigger.LESSON_WATCHED);
  }

  private Achievement findAchievement(StaticAchievementsKeys key) {
    return this.staticAchievementRepository.findByKey(key.name()).orElseThrow();
  }
}
