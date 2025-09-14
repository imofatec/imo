package com.imo.backend.modules.certificate;

import com.imo.backend.modules.base.Entity;
import com.imo.backend.modules.certificate.values_objects.CertificatePeriod;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

  public Certificate() {
  }

  public Certificate(
      String userId,
      String courseId,
      CertificatePeriod certificatePeriod,
      LocalDateTime issuedAt
  ) {
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
