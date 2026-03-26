package com.imo.backend.unit.certification;

import com.imo.backend.contexts.certification.CertificatePolicies;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CertificatePolicyTest {
  @Mock
  private ProgressRepository repository;

  @InjectMocks
  CertificatePolicies policies;

  @Test
  @DisplayName("happy path: emissão de certificado com curso finalizado")
  void shouldAssertIsFinished() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lessonId), 1);

    when(this.repository.findByUserIdAndCourseId(
        userId,
        courseId
    )).thenReturn(Optional.of(progress));

    assertDoesNotThrow(() -> this.policies.assertCourseIsFinished(userId, courseId));

    verify(this.repository).findByUserIdAndCourseId(userId, courseId);
  }

  @Test
  @DisplayName("exception: tentativa de emissão com curso em andamento")
  void shouldThrowForbiddenWhenInProgress() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lessonId), 3);

    when(this.repository.findByUserIdAndCourseId(
        userId,
        courseId
    )).thenReturn(Optional.of(progress));

    assertThrows(
        ForbiddenException.class,
        () -> this.policies.assertCourseIsFinished(userId, courseId)
    );

    verify(this.repository).findByUserIdAndCourseId(userId, courseId);
  }

  @Test
  @DisplayName("exception: tentativa de emissão com progresso inexistente")
  void shouldThrowNotFoundWhenNonExistentProgress() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();

    when(this.repository.findByUserIdAndCourseId(userId, courseId)).thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () -> this.policies.assertCourseIsFinished(userId, courseId)
    );

    verify(this.repository).findByUserIdAndCourseId(userId, courseId);
  }
}
