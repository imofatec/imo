package com.imo.backend.contexts.catalog.skill.repositories;

import com.imo.backend.contexts.catalog.skill.Skill;
import java.util.List;

public interface CustomSkillRepository {
  List<Skill> findAllByCategorySlug(String categorySlug);
}
