package com.imo.backend.contexts.recommendation;

import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.MongoDB;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("recommendations")
@Data
public class Recommendation extends Entity {
  @Indexed(unique = true)
  private ObjectId userId;

  private List<ObjectId> courseIds;

  public Recommendation() {}

  public Recommendation(String userId, List<String> courseIds) {
    this.setUserId(userId);
    this.setCourseIds(courseIds);
  }

  public void setUserId(String userId) {
    this.userId = new ObjectId(userId);
  }

  public String getUserId() {
    return this.userId.toString();
  }

  public void setCourseIds(List<String> courseIds) {
    if (courseIds == null || courseIds.isEmpty()) {
      this.courseIds = List.of();
      return;
    }

    courseIds.forEach(MongoDB::validateObjectId);
    this.courseIds = courseIds.stream().map(ObjectId::new).toList();
  }

  public List<String> getCourseIdsAsString() {
    if (this.courseIds == null || this.courseIds.isEmpty()) {
      return List.of();
    }

    return this.courseIds.stream().map(ObjectId::toString).toList();
  }
}
