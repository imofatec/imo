package com.imo.backend.contexts.learning_path.skill_profile.lib;

public interface SkillCoverageCalculator {
  int calculateNextCoverage(int currentCoverage, int isEssential);
}
