package com.imo.backend.e2e.skill_profile.helpers;

import static org.awaitility.Awaitility.await;

import com.imo.backend.contexts.skill_profile.SkillProfile;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class SkillProfileTestHelper {
  private SkillProfileTestHelper() {}

  public static List<SkillProfile> waitForSkillProfiles(
      SkillProfileRepository skillProfileRepository, String userId, int expectedCount) {
    AtomicReference<List<SkillProfile>> skillProfilesRef = new AtomicReference<>(List.of());

    await()
        .atMost(Duration.ofSeconds(2))
        .pollInterval(Duration.ofMillis(100))
        .until(
            () -> {
              List<SkillProfile> skillProfiles = skillProfileRepository.findAllByUserId(userId);
              skillProfilesRef.set(skillProfiles);
              return skillProfiles.size() == expectedCount;
            });

    return skillProfilesRef.get();
  }
}
