package com.imo.backend.contexts.journey_tracking.events;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProgressEventListener {
  private final ProgressRepository progressRepository;

  private final LessonRepository lessonRepository;

  public ProgressEventListener(
      ProgressRepository progressRepository,
      LessonRepository lessonRepository
  ) {
    this.progressRepository = progressRepository;
    this.lessonRepository = lessonRepository;
  }

  @ApplicationModuleListener
  public void execute(ReevaluateProgressEvent event) {
    int page = 0;
    int size = 100;
    List<Progress> progressList;

    var courseId = event.courseId();
    do {
      progressList = this.progressRepository.findProgressByCourseId(courseId, page, size);

      progressList.forEach(progress -> {
         List<String> existingLessonsIds = this.lessonRepository.findAllByCourseId(progress.getCourseId())
        .stream()
        .map(Entity::getId)
        .toList();
        
        boolean changed = progress.assertReevaluateStructure(existingLessonsIds);

        if (changed) {
          this.progressRepository.save(progress);
        }
      });

      page++;
    } while (progressList.size() == size);
  }
}
