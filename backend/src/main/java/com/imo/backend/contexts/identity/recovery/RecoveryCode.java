package com.imo.backend.contexts.identity.recovery;

import com.imo.backend.contexts.common.Entity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("recovery_codes")
@Data
public class RecoveryCode extends Entity {
  private ObjectId userId;

  private String code;

  private boolean wasUsed;

  // Expires exactly at the timestamp stored in expiresAt.
  @Indexed(expireAfter = "0s")
  private LocalDateTime expiresAt;

  public RecoveryCode() {}

  public RecoveryCode(String userId, String code) {
    this.setUserId(userId);
    this.code = code;
    this.expiresAt = LocalDateTime.now().plusMinutes(10);
  }

  public void setUserId(String userId) {
    this.userId = new ObjectId(userId);
  }

  public String getUserId() {
    return this.userId.toString();
  }
}
