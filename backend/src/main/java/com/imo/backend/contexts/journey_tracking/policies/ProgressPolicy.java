package com.imo.backend.contexts.journey_tracking.policies;

import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProgressPolicy {
  private final ProgressRepository progressRepository;

  private final LessonRepository lessonRepository;

  public ProgressPolicy(
      ProgressRepository progressRepository,
      LessonRepository lessonRepository
  ) {
    this.progressRepository = progressRepository;
    this.lessonRepository = lessonRepository;
  }

  public void execute(String userId, String courseId) {
    try {
      this.progressRepository.findByUserIdAndCourseIdOrThrow(userId, courseId);
      throw new ConflictException("Progresso ja foi inicado");
    } catch (NotFoundException ignored) {
    }
  }

  public boolean reevaluate(Progress progress) {
    List<String> existingLessonsIds = this.lessonRepository.findAllByCourseId(progress.getCourseId())
        .stream()
        .map(Entity::getId)
        .toList();

    return progress.assertReevaluateStructure(existingLessonsIds);
  }
}
