package com.imo.backend.contexts.learning_path.skill_profile;

import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("skill_profile")
@CompoundIndex(
    name = "uk_skill_profile_user_skill",
    def = "{'userId': 1, 'skillId': 1}",
    unique = true)
@Data
public class SkillProfile extends Entity {
  private ObjectId userId;

  private ObjectId skillId;

  private int coverage;

  public SkillProfile() {}

  public SkillProfile(String userId, String skillId, int coverage) {
    this.setUserId(userId);
    this.setSkillId(skillId);
    this.setCoverage(coverage);
  }

  public void setUserId(String userId) {
    this.userId = new ObjectId(userId);
  }

  public String getUserId() {
    return this.userId.toString();
  }

  public void setSkillId(String skillId) {
    this.skillId = new ObjectId(skillId);
  }

  public String getSkillId() {
    return this.skillId.toString();
  }

  public void setCoverage(int coverage) {
    if (coverage < 0 || coverage > 100) {
      throw new BadRequestException("Coverage deve estar entre 0 e 100");
    }

    this.coverage = coverage;
  }
}
