package com.imo.backend.contexts.social.comment;

import com.imo.backend.contexts.common.Entity;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("comments")
public class Comment extends Entity {
  // relations
  private ObjectId userId;

  private ObjectId lessonId;

  @Nullable
  private ObjectId parentId;

  // properties
  @Getter
  @Setter
  private String content;

  public Comment() {
  }

  public Comment(String userId, String lessonId, String parentId, String content) {
    this.setUserId(userId);
    this.setLessonId(lessonId);
    this.setParentId(parentId);
    this.setContent(content);
  }

  public String getUserId() {
    return userId.toString();
  }

  public void setUserId(String userId) {
    this.userId = new ObjectId(userId);
  }

  public String getLessonId() {
    return lessonId.toString();
  }

  public void setLessonId(String lessonId) {
    this.lessonId = new ObjectId(lessonId);
  }

  public String getParentId() {
    return (parentId == null) ? null : parentId.toString();
  }

  public void setParentId(String parentId) {
    this.parentId = (parentId == null) ? null : new ObjectId(parentId);
  }
}
