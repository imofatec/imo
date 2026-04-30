package com.imo.backend.contexts.learning_path.skill_profile.repositories;

import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillProfileRepository
    extends MongoRepository<SkillProfile, String>, CustomSkillProfileRepository {
  Optional<SkillProfile> findByUserIdAndSkillId(ObjectId userId, ObjectId skillId);

  List<SkillProfile> findAllByUserId(ObjectId userId);

  default Optional<SkillProfile> findByUserIdAndSkillId(String userId, String skillId) {
    return this.findByUserIdAndSkillId(new ObjectId(userId), new ObjectId(skillId));
  }

  default List<SkillProfile> findAllByUserId(String userId) {
    return this.findAllByUserId(new ObjectId(userId));
  }
}
