package com.imo.backend.models.lessons.dtos;

import com.imo.backend.models.lessons.Lesson;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
public class NoCommentsLesson {
  @MongoId(FieldType.OBJECT_ID)
  private String id;

  private int index;

  private String title;

  private String description;

  private String youtubeLink;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public NoCommentsLesson(Lesson lesson) {
    this.id = lesson.getId();
    this.index = lesson.getIndex();
    this.title = lesson.getTitle();
    this.description = lesson.getDescription();
    this.youtubeLink = lesson.getYoutubeLink();
    this.createdAt = lesson.getCreatedAt();
    this.updatedAt = lesson.getUpdatedAt();
  }
}
