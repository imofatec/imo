package com.imo.backend.contexts.recognition;

import com.imo.backend.contexts.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("user_achievements")
@CompoundIndex(
    name = "uk_user_achievement",
    def = "{ 'userId': 1, 'achievementKey': 1 }",
    unique = true)
@Data
public class UserAchievement extends Entity {
  private ObjectId userId;

  private String achievementKey;

  public UserAchievement(String userId, String achievementKey) {
    this.setUserId(userId);
    this.achievementKey = achievementKey;
  }

  public UserAchievement() {}

  public String getUserId() {
    return this.userId.toString();
  }

  public void setUserId(String userId) {
    this.userId = new ObjectId(userId);
  }
}
