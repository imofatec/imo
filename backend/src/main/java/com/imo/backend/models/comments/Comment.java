package com.imo.backend.models.comments;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class Comment {
  private String id;

  private String userId;

  private String username;

  private String comment;

  private LocalDateTime createdAt;

  public Comment(String comment) {
    this.comment = comment;
  }

  public static Comment fromDTO(String userId, String username, String comment){
    Comment newComment = new Comment(comment);
    newComment.setId(new ObjectId().toString());
    newComment.setUserId(userId);
    newComment.setUsername(username);
    newComment.setCreatedAt(LocalDateTime.now());
    return newComment;
  }
}
