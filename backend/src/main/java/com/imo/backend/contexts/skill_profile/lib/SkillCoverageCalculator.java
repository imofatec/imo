package com.imo.backend.contexts.skill_profile.lib;

public interface SkillCoverageCalculator {
  int calculateNextCoverage(int currentCoverage, int isEssential);
}
