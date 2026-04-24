package com.imo.backend.contexts.skill_profile;

import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("skillProfile")
@Data
public class SkillProfile extends Entity {
  private ObjectId userId;

  private ObjectId skillId;

  private int coverage;

  private List<ObjectId> completedCourseIds = new ArrayList<>();

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

  public List<String> getCompletedCourseIds() {
    this.ensureCompletedCourseIds();
    return this.completedCourseIds.stream().map(ObjectId::toString).toList();
  }

  public boolean hasCompletedCourse(String courseId) {
    this.ensureCompletedCourseIds();
    return this.completedCourseIds.contains(new ObjectId(courseId));
  }

  public void addCompletedCourseId(String courseId) {
    this.ensureCompletedCourseIds();
    ObjectId courseObjectId = new ObjectId(courseId);

    if (!this.completedCourseIds.contains(courseObjectId)) {
      this.completedCourseIds.add(courseObjectId);
    }
  }

  private void ensureCompletedCourseIds() {
    if (this.completedCourseIds == null) {
      this.completedCourseIds = new ArrayList<>();
    }
  }
}
