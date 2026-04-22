package com.imo.backend.contexts.identity.achievement;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AchievementCatalog {
  private static final EnumMap<AchievementCode, AchievementDefinition> catalogByCode =
      buildCatalog();

  private static final EnumMap<AchievementTrigger, List<AchievementDefinition>> catalogByTrigger =
      buildCatalogByTrigger();

  public static AchievementDefinition findByCode(AchievementCode code) {
    return catalogByCode.get(code);
  }

  public static List<AchievementDefinition> findByTrigger(AchievementTrigger trigger) {
    return catalogByTrigger.get(trigger);
  }

  private static EnumMap<AchievementCode, AchievementDefinition> buildCatalog() {
    EnumMap<AchievementCode, AchievementDefinition> catalog = new EnumMap<>(AchievementCode.class);

    catalog.put(
        AchievementCode.FIRST_COURSE,
        new AchievementDefinition(
            AchievementCode.FIRST_COURSE,
            "Primeiro curso finalizado",
            "Finalize seu primeiro curso.",
            AchievementTrigger.COURSE_FINISHED,
            1));

    catalog.put(
        AchievementCode.TEN_COURSES,
        new AchievementDefinition(
            AchievementCode.TEN_COURSES,
            "10 cursos finalizados",
            "Finalize 10 cursos.",
            AchievementTrigger.COURSE_FINISHED,
            10));

    catalog.put(
        AchievementCode.TEN_LESSONS,
        new AchievementDefinition(
            AchievementCode.TEN_LESSONS,
            "10 aulas assistidas",
            "Assista 10 aulas.",
            AchievementTrigger.LESSON_WATCHED,
            10));

    return catalog;
  }

  private static EnumMap<AchievementTrigger, List<AchievementDefinition>> buildCatalogByTrigger() {
    return catalogByCode.values().stream()
        .collect(
            Collectors.groupingBy(
                AchievementDefinition::trigger,
                () -> new EnumMap<>(AchievementTrigger.class),
                Collectors.toUnmodifiableList()));
  }
}
