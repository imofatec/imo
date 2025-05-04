package com.imo.backend.models.lessons;

import com.imo.backend.models.entity.Entity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.imo.backend.models.comments.Comment;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@EqualsAndHashCode(callSuper = true)
@Data
public class Lesson extends Entity {
  private int index;

  private String title;

  private String description;

  private String youtubeLink;

  private List<Comment> comments = new ArrayList<>();

  public void setYoutubeLink(String youtubeLink) {
    int left = youtubeLink.indexOf("=");
    int right = youtubeLink.indexOf("&");

    if (right != -1) {
      this.youtubeLink = youtubeLink.substring(left + 1, right);
      return;
    }

    if (left != -1) {
      this.youtubeLink = youtubeLink.substring(left + 1);
      return;
    }

    this.youtubeLink = youtubeLink;
  }
}
