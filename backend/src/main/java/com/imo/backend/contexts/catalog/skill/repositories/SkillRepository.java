package com.imo.backend.contexts.catalog.skill.repositories;

import com.imo.backend.contexts.catalog.skill.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends MongoRepository<Skill, String>, CustomSkillRepository {}
