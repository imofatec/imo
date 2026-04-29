package com.imo.backend.contexts.learning_path.skill_profile.http.dtos;

import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfileDetails;

public record SkillProfileDTO(String id, String userId, SkillProfileSkillDTO skill, int coverage) {
  public static SkillProfileDTO fromEntity(SkillProfile skillProfile, Skill skill) {
    return new SkillProfileDTO(
        skillProfile.getId(),
        skillProfile.getUserId(),
        SkillProfileSkillDTO.fromEntity(skill),
        skillProfile.getCoverage());
  }

  public static SkillProfileDTO fromDetails(SkillProfileDetails skillProfileDetails) {
    return fromEntity(skillProfileDetails.skillProfile(), skillProfileDetails.skill());
  }

  public record SkillProfileSkillDTO(String id, String name, Category category, int isEssential) {
    public static SkillProfileSkillDTO fromEntity(Skill skill) {
      return new SkillProfileSkillDTO(
          skill.getId(), skill.getName(), skill.getCategory(), skill.getIsEssential());
    }
  }
}
