package com.imo.backend.contexts.journey_tracking;

import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document("progress")
@Data
public class Progress extends Entity {
  //  relations
  private ObjectId userId;

  private ObjectId courseId;

  private List<ObjectId> lessonsWatched = new ArrayList<>();

  //  attributes
  private ProgressPeriod progressPeriod;

  private ProgressStatus status;

  public Progress() {
  }

  public Progress(
      String userId,
      String courseId,
      List<String> lessonsWatched,
      int totalLessonsInCourse
  ) {
    if (lessonsWatched == null || lessonsWatched.isEmpty()) {
      throw new BadRequestException("Progresso deve ter ao menos 1 aula assistida");
    }
    this.setUserId(userId);
    this.setCourseId(courseId);
    this.status = ProgressStatus.IN_PROGRESS;
    for (String lesson : lessonsWatched) {
      this.watchLesson(lesson, totalLessonsInCourse);
    }
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

  public List<String> getLessonsWatched() {
    return this.lessonsWatched.stream().map(ObjectId::toString).toList();
  }

  public void watchLesson(String lessonToWatch, int totalLessonsInCourse) {
    if (this.getLessonsWatched().size() >= totalLessonsInCourse) {
      throw new BadRequestException("Total de aulas no curso estão excedendo");
    }

    if (this.getLessonsWatched().contains(lessonToWatch)) {
      throw new ConflictException("Aula ja foi concluida anteriormente");
    }

    this.lessonsWatched.add(new ObjectId(lessonToWatch));

    if (this.progressPeriod == null) {
      this.progressPeriod = new ProgressPeriod(LocalDateTime.now(), null);
    }

    if (this.lessonsWatched.size() == totalLessonsInCourse) {
      this.progressPeriod = new ProgressPeriod(
          this.progressPeriod.startedAt(),
          LocalDateTime.now()
      );
      this.status = ProgressStatus.FINISHED;
    }
  }

  public boolean assertReevaluateStructure(List<String> existingLessonsIds) {
    boolean changed = false;
    Set<String> existingLessonsIdsSet = Set.copyOf(existingLessonsIds);

    List<String> filteredWatchedLessons = this
        .getLessonsWatched()
        .stream()
        .filter(existingLessonsIdsSet::contains)
        .toList();

    if (filteredWatchedLessons.size() != this.lessonsWatched.size()) {
      this.lessonsWatched = filteredWatchedLessons.stream().map(ObjectId::new).toList();
      changed = true;
    }

    int totalLessons = existingLessonsIds.size();
    boolean shouldBeFinished = totalLessons > 0 && this.lessonsWatched.size() == totalLessons;

    if (shouldBeFinished && this.status != ProgressStatus.FINISHED) {
      this.progressPeriod = new ProgressPeriod(
          this.progressPeriod.startedAt(),
          LocalDateTime.now()
      );
      this.status = ProgressStatus.FINISHED;
      changed = true;
    }

    if (!shouldBeFinished && this.status == ProgressStatus.FINISHED) {
      this.progressPeriod = new ProgressPeriod(this.progressPeriod.startedAt(), null);
      this.status = ProgressStatus.IN_PROGRESS;
      changed = true;
    }

    return changed;
  }
}
