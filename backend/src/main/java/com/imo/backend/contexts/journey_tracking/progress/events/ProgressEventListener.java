package com.imo.backend.contexts.journey_tracking.progress.events;

import com.imo.backend.contexts.catalog.lesson.events.LessonsListUpdatedEvent;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.journey_tracking.progress.repositories.ProgressRepository;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ProgressEventListener {
  private final ProgressRepository progressRepository;

  private final LessonRepository lessonRepository;

  public ProgressEventListener(
      ProgressRepository progressRepository, LessonRepository lessonRepository) {
    this.progressRepository = progressRepository;
    this.lessonRepository = lessonRepository;
  }

  @Async
  @EventListener
  public void execute(LessonsListUpdatedEvent event) {
    int page = 0;
    int size = 100;
    List<Progress> progressList;

    var courseId = event.courseId();
    do {
      progressList = this.progressRepository.findProgressByCourseId(courseId, page, size);

      progressList.forEach(
          progress -> {
            List<String> existingLessonsIds =
                this.lessonRepository.findAllByCourseId(progress.getCourseId()).stream()
                    .map(Entity::getId)
                    .toList();

            boolean changed = progress.reevaluateStructure(existingLessonsIds);

            if (changed) {
              this.progressRepository.save(progress);
            }
          });

      page++;
    } while (progressList.size() == size);
  }
}
