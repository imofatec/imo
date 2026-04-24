package com.imo.backend.contexts.skill_profile.repositories;

import com.imo.backend.contexts.skill_profile.SkillProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillProfileRepository extends MongoRepository<SkillProfile, String> {}
