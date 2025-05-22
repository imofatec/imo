package com.imo.backend.models.comments;

import com.imo.backend.models.comments.dto.ConvertedCommentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class Comment {
  @MongoId(FieldType.OBJECT_ID)
  private String id;

  private ObjectId userId;

  private String username;

  private String comment;

  private ObjectId parentId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public Comment(String comment) {
    this.comment = comment;
  }

  public static Comment fromDTO(String userId, String username, String comment, String parentId) {
    Comment newComment = new Comment(comment);
    if (parentId != null) {
      newComment.setParentId(new ObjectId(parentId));
    }
    newComment.setId(new ObjectId().toString());
    newComment.setUserId(new ObjectId(userId));
    newComment.setUsername(username);
    newComment.setCreatedAt(LocalDateTime.now());
    newComment.setUpdatedAt(LocalDateTime.now());
    return newComment;
  }

  public static ConvertedCommentDto toDTO(Comment comment) {
    return new ConvertedCommentDto(
        comment.getId(), comment.getUserId().toString(), comment.getUsername(),
        comment.getComment(), comment.getParentId() != null ? comment.getParentId().toString() : null, comment.getCreatedAt(), comment.getUpdatedAt()
    );
  }
}
