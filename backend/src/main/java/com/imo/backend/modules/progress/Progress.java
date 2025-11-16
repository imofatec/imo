package com.imo.backend.modules.progress;

import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.modules.base.Entity;
import com.imo.backend.modules.progress.value_objects.ProgressPeriod;
import com.imo.backend.modules.progress.value_objects.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document("progress")
@Data
@AllArgsConstructor
public class Progress extends Entity {
  //  relations
  private ObjectId userId;

  private ObjectId courseId;

  private List<ObjectId> lessonsWatched;

  //  attributes
  private ProgressPeriod progressPeriod;

  private ProgressStatus status;

  public Progress() {
  }

  public Progress(
      String userId,
      String courseId,
      List<String> lessonsWatched,
      ProgressPeriod progressPeriod,
      ProgressStatus status
  ) {
    this.setUserId(userId);
    this.setCourseId(courseId);
    this.setLessonsWatched(lessonsWatched);
    this.setProgressPeriod(progressPeriod);
    this.setStatus(status);
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

  public void setLessonsWatched(List<String> lessonsWatched) {
    this.lessonsWatched = lessonsWatched.stream().map(ObjectId::new).toList();
  }

  public List<String> getLessonsWatched() {
    return this.lessonsWatched.stream().map(ObjectId::toString).toList();
  }

  public void watchLesson(String lessonToWatch, int totalLessons) {
    if (this.lessonsWatched.size() == totalLessons) {
      return;
    }

    if (this.getLessonsWatched().contains(lessonToWatch)) {
      throw new ConflictException("Aula ja foi concluida anteriormente");
    }

    this.lessonsWatched.add(new ObjectId(lessonToWatch));

    if (this.lessonsWatched.size() == totalLessons) {
      this.progressPeriod = new ProgressPeriod(
          this.progressPeriod.startedAt(),
          LocalDateTime.now()
      );

      this.status = ProgressStatus.FINISHED;
    }
  }
}
