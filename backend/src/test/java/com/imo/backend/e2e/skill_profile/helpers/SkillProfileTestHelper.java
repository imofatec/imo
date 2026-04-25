package com.imo.backend.e2e.skill_profile.helpers;

import com.imo.backend.contexts.skill_profile.SkillProfile;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import java.util.List;

public final class SkillProfileTestHelper {
  private SkillProfileTestHelper() {}

  public static List<SkillProfile> waitForSkillProfiles(
      SkillProfileRepository skillProfileRepository, String userId, int expectedCount)
      throws InterruptedException {
    for (int attempt = 0; attempt < 20; attempt++) {
      List<SkillProfile> skillProfiles = skillProfileRepository.findAllByUserId(userId);
      if (skillProfiles.size() == expectedCount) {
        return skillProfiles;
      }

      Thread.sleep(100);
    }

    return skillProfileRepository.findAllByUserId(userId);
  }
}
