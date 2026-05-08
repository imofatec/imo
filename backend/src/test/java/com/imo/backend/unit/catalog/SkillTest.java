package com.imo.backend.unit.catalog;

import static org.junit.jupiter.api.Assertions.*;

import com.imo.backend.contexts.catalog.course.Categories;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SkillTest {
  private static final Categories BASE_CATEGORY = Categories.FUNDAMENTALS;

  @Test
  @DisplayName("happy path (constructor): criar skill válida")
  void shouldCreateValidSkill() {
    Skill skill = new Skill(BASE_CATEGORY, "POO", 3);

    assertAll(
        () -> assertEquals("Fundamentos", skill.getCategory().name()),
        () -> assertEquals("POO", skill.getName()),
        () -> assertEquals(3, skill.getIsEssential()));
  }

  @Test
  @DisplayName("happy path (constructor): remover espaços nas bordas do nome")
  void shouldTrimNameWhenCreatingSkill() {
    Skill skill = new Skill(BASE_CATEGORY, "  POO  ", 2);

    assertEquals("POO", skill.getName());
  }

  @Test
  @DisplayName("exception (setName): rejeitar nome nulo, vazio ou em branco")
  void shouldThrowBadRequestWhenNameIsInvalid() {
    assertThrows(BadRequestException.class, () -> new Skill(BASE_CATEGORY, "", 2));
  }

  @Test
  @DisplayName("happy path (IsEssential): aceitar limite inferior")
  void shouldAcceptIsEssentialAtLowerBound() {
    Skill skill = new Skill(BASE_CATEGORY, "POO", 0);

    assertEquals(0, skill.getIsEssential());
  }

  @Test
  @DisplayName("happy path (IsEssential): aceitar limite superior")
  void shouldAcceptIsEssentialAtUpperBound() {
    Skill skill = new Skill(BASE_CATEGORY, "POO", 3);

    assertEquals(3, skill.getIsEssential());
  }
}
