package com.imo.backend.unit.journey_tracking.progress_milestone;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.journey_tracking.progress.ProgressDetails;
import com.imo.backend.contexts.journey_tracking.progress.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestonePolicies;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProgressMilestonePoliciesTest {
  @Mock private ProgressRepository repository;

  @InjectMocks private ProgressMilestonePolicies policies;

  @Test
  @DisplayName("happy path: criação de marco com curso finalizado")
  void shouldAllowMilestoneCreationWhenCourseIsFinished() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lessonId), 1);

    when(this.repository.findDetailsByUserIdAndCourseId(userId, courseId))
        .thenReturn(Optional.of(new ProgressDetails(progress, null, null, List.of())));

    assertDoesNotThrow(() -> this.policies.assertCourseIsFinished(userId, courseId));

    verify(this.repository).findDetailsByUserIdAndCourseId(userId, courseId);
  }

  @Test
  @DisplayName("exception: tentativa de criação com curso em andamento")
  void shouldThrowForbiddenWhenCourseIsInProgress() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lessonId), 3);

    when(this.repository.findDetailsByUserIdAndCourseId(userId, courseId))
        .thenReturn(Optional.of(new ProgressDetails(progress, null, null, List.of())));

    assertThrows(
        ForbiddenException.class, () -> this.policies.assertCourseIsFinished(userId, courseId));

    verify(this.repository).findDetailsByUserIdAndCourseId(userId, courseId);
  }

  @Test
  @DisplayName("exception: tentativa de criação com progresso inexistente")
  void shouldThrowNotFoundWhenProgressDoesNotExist() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();

    when(this.repository.findDetailsByUserIdAndCourseId(userId, courseId))
        .thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class, () -> this.policies.assertCourseIsFinished(userId, courseId));

    verify(this.repository).findDetailsByUserIdAndCourseId(userId, courseId);
  }
}
