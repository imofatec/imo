package com.imo.backend.unit.skill_profile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.imo.backend.lib.skill_profile.WeightedSkillCoverageCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeightedSkillCoverageCalculatorTest {
  private final WeightedSkillCoverageCalculator skillCoverageCalculator =
      new WeightedSkillCoverageCalculator();

  @Test
  @DisplayName("happy path (calculateNextCoverage): incrementar coverage usando essencialidade")
  void shouldCalculateNextCoverageBasedOnEssentiality() {
    assertEquals(0, this.skillCoverageCalculator.calculateNextCoverage(0, 0));
    assertEquals(8, this.skillCoverageCalculator.calculateNextCoverage(0, 1));
    assertEquals(18, this.skillCoverageCalculator.calculateNextCoverage(0, 2));
    assertEquals(30, this.skillCoverageCalculator.calculateNextCoverage(0, 3));
  }

  @Test
  @DisplayName("happy path (calculateNextCoverage): calcular incremento sobre coverage restante")
  void shouldCalculateCoverageIncrementUsingRemainingCoverage() {
    assertEquals(54, this.skillCoverageCalculator.calculateNextCoverage(50, 1));
    assertEquals(59, this.skillCoverageCalculator.calculateNextCoverage(50, 2));
    assertEquals(65, this.skillCoverageCalculator.calculateNextCoverage(50, 3));
  }

  @Test
  @DisplayName("happy path (calculateNextCoverage): limitar coverage em 100")
  void shouldLimitCoverageToOneHundred() {
    assertEquals(100, this.skillCoverageCalculator.calculateNextCoverage(99, 3));
    assertEquals(100, this.skillCoverageCalculator.calculateNextCoverage(100, 3));
  }
}
