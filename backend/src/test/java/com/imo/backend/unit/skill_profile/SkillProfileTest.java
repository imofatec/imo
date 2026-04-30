package com.imo.backend.unit.skill_profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SkillProfileTest {

  @Test
  @DisplayName("happy path (constructor): criar skillProfile válido")
  void shouldCreateValidSkillProfile() {
    String userId = new ObjectId().toString();
    String skillId = new ObjectId().toString();

    SkillProfile skillProfile = new SkillProfile(userId, skillId, 35);

    assertEquals(userId, skillProfile.getUserId());
    assertEquals(skillId, skillProfile.getSkillId());
    assertEquals(35, skillProfile.getCoverage());
  }

  @Test
  @DisplayName("happy path (setCoverage): aceitar limites 0 e 100")
  void shouldAcceptCoverageLimits() {
    String userId = new ObjectId().toString();
    String skillId = new ObjectId().toString();
    SkillProfile skillProfile = new SkillProfile(userId, skillId, 0);

    assertEquals(0, skillProfile.getCoverage());

    skillProfile.setCoverage(100);

    assertEquals(100, skillProfile.getCoverage());
  }

  @Test
  @DisplayName("exception (setCoverage): rejeitar coverage fora da escala")
  void shouldRejectInvalidCoverage() {
    String userId = new ObjectId().toString();
    String skillId = new ObjectId().toString();

    assertThrows(BadRequestException.class, () -> new SkillProfile(userId, skillId, -1));
    assertThrows(BadRequestException.class, () -> new SkillProfile(userId, skillId, 101));
  }
}
