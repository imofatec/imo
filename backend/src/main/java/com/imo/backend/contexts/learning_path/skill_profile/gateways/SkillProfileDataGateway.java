package com.imo.backend.contexts.learning_path.skill_profile.gateways;

import com.imo.backend.contexts.catalog.skill.Skill;
import java.util.List;

public interface SkillProfileDataGateway {
  void assertUserExists(String userId);

  List<Skill> findSkillsByCourseIdOrThrow(String courseId);
}
