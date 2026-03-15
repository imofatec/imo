package com.imo.backend.contexts.journey_tracking.events;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.policies.ProgressPolicy;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProgressEventListener {
  private final ProgressRepository progressRepository;

  private final ProgressPolicy progressPolicy;

  public ProgressEventListener(
      ProgressRepository progressRepository,
      ProgressPolicy progressPolicy
  ) {
    this.progressRepository = progressRepository;
    this.progressPolicy = progressPolicy;
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
        boolean changed = this.progressPolicy.reevaluate(progress);

        if (changed) {
          this.progressRepository.save(progress);
        }
      });

      page++;
    } while (progressList.size() == size);
  }
}
