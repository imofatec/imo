package com.imo.backend.contexts.journey_tracking;

import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.journey_tracking.commands.UpdateProgressCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    this.lessonsWatched = new ArrayList<>(lessonsWatched.stream().map(ObjectId::new).toList());
  }

  public List<String> getLessonsWatched() {
    return this.lessonsWatched.stream().map(ObjectId::toString).toList();
  }

  public void progressUpdater(UpdateProgressCommand cmd) {
    if (cmd.progressPeriod() != null) {
      this.progressPeriod = cmd.progressPeriod();
    }

    if (cmd.status() != null) {
      this.status = cmd.status();
    }

    if (cmd.lessonsWatched() != null) {
      this.setLessonsWatched(cmd.lessonsWatched());
    }
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

  public boolean assertReevaluateStructure(List<String> existingLessonsIds) {
    boolean changed = false;
    Set<String> existingLessonsIdsSet = Set.copyOf(existingLessonsIds);

    List<String> filteredWatchedLessons = this.getLessonsWatched()
        .stream()
        .filter(existingLessonsIdsSet::contains)
        .toList();

    if (filteredWatchedLessons.size() != this.lessonsWatched.size()) {
      this.setLessonsWatched(filteredWatchedLessons);
      changed = true;
    }

    int totalLessons = existingLessonsIds.size();
    boolean shouldBeFinished = totalLessons > 0 && this.lessonsWatched.size() == totalLessons;

    if (shouldBeFinished && this.status != ProgressStatus.FINISHED) {
      this.progressPeriod = new ProgressPeriod(this.progressPeriod.startedAt(), LocalDateTime.now());
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
