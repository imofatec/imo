package com.imo.backend.e2e.catalog.helpers;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CatalogSkillTestHelper {
  private static Map<Categories, List<String>> skillIdsByCategory = Map.of();

  private CatalogSkillTestHelper() {}

  public static void initialize(List<Skill> skills) {
    skillIdsByCategory =
        skills.stream()
            .collect(
                Collectors.groupingBy(
                    skill ->
                        Categories.getAll().stream()
                            .filter(
                                category -> category.getValue().equals(skill.getCategory().name()))
                            .findFirst()
                            .orElseThrow(
                                () ->
                                    new BadRequestException(
                                        "Categoria de skill de teste inválida: "
                                            + skill.getCategory().name())),
                    Collectors.mapping(Skill::getId, Collectors.toList())));
  }

  public static void clear() {
    skillIdsByCategory = Map.of();
  }

  public static List<String> getSkillIdsForCategory(Categories category, int limit) {
    List<String> skillIds = skillIdsByCategory.get(category);
    if (skillIds == null || skillIds.isEmpty()) {
      throw new BadRequestException(
          "Nenhuma skill de teste foi carregada para a categoria " + category.name());
    }

    int safeLimit = Math.min(limit, skillIds.size());
    return skillIds.subList(0, safeLimit);
  }
}
