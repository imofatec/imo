package com.imo.backend.contexts.certification;

import com.imo.backend.contexts.certification.values_objects.CertificatePeriod;
import com.imo.backend.contexts.common.Entity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("certificates")
@Data
public class Certificate extends Entity {
  //  relations
  private ObjectId userId;
  private ObjectId courseId;

  //  attributes
  private CertificatePeriod certificatePeriod;

  private LocalDateTime issuedAt;

  public Certificate() {}

  public Certificate(
      String userId, String courseId, CertificatePeriod certificatePeriod, LocalDateTime issuedAt) {
    this.setUserId(userId);
    this.setCourseId(courseId);
    this.setCertificatePeriod(certificatePeriod);
    this.setIssuedAt(issuedAt);
  }

  public void setUserId(String userId) {
    this.userId = new ObjectId(userId);
  }

  public String getUserId() {
    return this.userId.toString();
  }

  public void setCourseId(String courseId) {
    this.courseId = new ObjectId(courseId);
  }

  public String getCourseId() {
    return this.courseId.toString();
  }
}
