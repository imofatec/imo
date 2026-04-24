package com.imo.backend.lib.skill_profile;

import com.imo.backend.contexts.skill_profile.lib.SkillCoverageCalculator;
import org.springframework.stereotype.Service;

@Service
public class WeightedSkillCoverageCalculator implements SkillCoverageCalculator {
  private static final double[] WEIGHTS_BY_ESSENTIAL = {0.0, 0.08, 0.18, 0.30};

  @Override
  public int calculateNextCoverage(int currentCoverage, int isEssential) {
    int remainingCoverage = 100 - currentCoverage;
    int coverageIncrement = (int) Math.ceil(remainingCoverage * WEIGHTS_BY_ESSENTIAL[isEssential]);

    return Math.min(100, currentCoverage + coverageIncrement);
  }
}
