package com.imo.backend.contexts.skill_profile.repositories;

import com.imo.backend.contexts.skill_profile.SkillProfileDetails;
import java.util.List;

public interface CustomSkillProfileRepository {
  List<SkillProfileDetails> findAllDetailsByUserId(String userId);

  List<String> findDistinctUserIdsBySkillIds(List<String> skillIds);
}
