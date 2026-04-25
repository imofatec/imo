package com.imo.backend.contexts.catalog.skill.http.dtos;

import com.imo.backend.contexts.catalog.course.http.dtos.CategoryDTO;
import com.imo.backend.contexts.catalog.skill.Skill;

public record SkillDTO(String id, String name, CategoryDTO category, int isEssential) {
  public static SkillDTO fromEntity(Skill skill) {
    return new SkillDTO(
        skill.getId(),
        skill.getName(),
        CategoryDTO.fromVO(skill.getCategory()),
        skill.getIsEssential());
  }
}
