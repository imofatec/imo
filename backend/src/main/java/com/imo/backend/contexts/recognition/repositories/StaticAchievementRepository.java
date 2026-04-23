package com.imo.backend.contexts.recognition.repositories;

import com.imo.backend.contexts.recognition.Achievement;
import com.imo.backend.contexts.recognition.AchievementTrigger;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StaticAchievementRepository implements AchievementRepository {
  private static final String LOCKED_IMAGE_PATH = "/images/achievements/locked/default.png";
  private static final String UNLOCKED_IMAGE_PATH_PREFIX = "/images/achievements/unlocked/";

  public enum StaticAchievementsKeys {
    FIRST_LESSON,
    FIRST_COURSE,
    THREE_COURSES,
    FIVE_COURSES,
    TEN_COURSES,
    TEN_LESSONS,
    FIFTY_LESSONS,
    HUNDRED_LESSONS
  }

  private static final List<Achievement> ITEMS =
      List.of(
          new Achievement(
              StaticAchievementsKeys.FIRST_LESSON.name(),
              "Primeira aula assistida",
              "Assista sua primeira aula.",
              AchievementTrigger.LESSON_WATCHED,
              1,
              1,
              UNLOCKED_IMAGE_PATH_PREFIX + "first-lesson.png",
              LOCKED_IMAGE_PATH),
          new Achievement(
              StaticAchievementsKeys.FIRST_COURSE.name(),
              "Primeiro curso finalizado",
              "Finalize seu primeiro curso.",
              AchievementTrigger.COURSE_FINISHED,
              1,
              2,
              UNLOCKED_IMAGE_PATH_PREFIX + "first-course.png",
              LOCKED_IMAGE_PATH),
          new Achievement(
              StaticAchievementsKeys.THREE_COURSES.name(),
              "3 cursos finalizados",
              "Finalize 3 cursos.",
              AchievementTrigger.COURSE_FINISHED,
              3,
              3,
              UNLOCKED_IMAGE_PATH_PREFIX + "three-courses.png",
              LOCKED_IMAGE_PATH),
          new Achievement(
              StaticAchievementsKeys.FIVE_COURSES.name(),
              "5 cursos finalizados",
              "Finalize 5 cursos.",
              AchievementTrigger.COURSE_FINISHED,
              5,
              4,
              UNLOCKED_IMAGE_PATH_PREFIX + "five-courses.png",
              LOCKED_IMAGE_PATH),
          new Achievement(
              StaticAchievementsKeys.TEN_COURSES.name(),
              "10 cursos finalizados",
              "Finalize 10 cursos.",
              AchievementTrigger.COURSE_FINISHED,
              10,
              5,
              UNLOCKED_IMAGE_PATH_PREFIX + "ten-courses.png",
              LOCKED_IMAGE_PATH),
          new Achievement(
              StaticAchievementsKeys.TEN_LESSONS.name(),
              "10 aulas assistidas",
              "Assista 10 aulas.",
              AchievementTrigger.LESSON_WATCHED,
              10,
              6,
              UNLOCKED_IMAGE_PATH_PREFIX + "ten-lessons.png",
              LOCKED_IMAGE_PATH),
          new Achievement(
              StaticAchievementsKeys.FIFTY_LESSONS.name(),
              "50 aulas assistidas",
              "Assista 50 aulas.",
              AchievementTrigger.LESSON_WATCHED,
              50,
              7,
              UNLOCKED_IMAGE_PATH_PREFIX + "fifty-lessons.png",
              LOCKED_IMAGE_PATH),
          new Achievement(
              StaticAchievementsKeys.HUNDRED_LESSONS.name(),
              "100 aulas assistidas",
              "Assista 100 aulas.",
              AchievementTrigger.LESSON_WATCHED,
              100,
              8,
              UNLOCKED_IMAGE_PATH_PREFIX + "hundred-lessons.png",
              LOCKED_IMAGE_PATH));

  @Override
  public Optional<Achievement> findByKey(String key) {
    return ITEMS.stream().filter(achievement -> achievement.getKey().equals(key)).findFirst();
  }

  @Override
  public List<Achievement> findAllOrderByDisplayOrderAsc() {
    return ITEMS.stream().sorted(Comparator.comparingInt(Achievement::getDisplayOrder)).toList();
  }

  @Override
  public List<Achievement> findAllByTriggerOrderByDisplayOrderAsc(AchievementTrigger trigger) {
    return ITEMS.stream()
        .filter(achievement -> achievement.getTrigger() == trigger)
        .sorted(Comparator.comparingInt(Achievement::getDisplayOrder))
        .toList();
  }
}
