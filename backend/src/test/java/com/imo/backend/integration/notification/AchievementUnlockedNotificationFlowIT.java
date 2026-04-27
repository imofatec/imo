package com.imo.backend.integration.notification;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.imo.backend.contexts.notification.http.dtos.AchievementUnlockedNotificationDTO;
import com.imo.backend.contexts.notification.lib.NotificationProvider;
import com.imo.backend.contexts.recognition.events.AchievementUnlockedEvent;
import com.imo.backend.contexts.recognition.repositories.AchievementRepository;
import com.imo.backend.contexts.recognition.repositories.StaticAchievementRepository.StaticAchievementsKeys;
import com.imo.backend.integration.BaseMessagingTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

class AchievementUnlockedNotificationFlowIT extends BaseMessagingTest {
  @MockitoBean private NotificationProvider notificationProvider;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private AchievementRepository achievementRepository;

  @Test
  @DisplayName(
      "happy path: (fluxo completo): publicar AchievementUnlockedEvent e verificar envio da notificação")
  void shouldSendNotificationWhenAchievementUnlockedEventIsPublished() {
    String userId = "user-123";

    var achievement =
        this.achievementRepository
            .findByKey(StaticAchievementsKeys.FIRST_COURSE.name())
            .orElseThrow();

    this.applicationEventPublisher.publishEvent(new AchievementUnlockedEvent(userId, achievement));

    verify(notificationProvider, timeout(10_000))
        .sendAchievementUnlocked(
            eq(userId),
            argThat(
                (AchievementUnlockedNotificationDTO notification) ->
                    notification.key().equals(StaticAchievementsKeys.FIRST_COURSE.name())
                        && notification.title().equals("Primeiro curso finalizado")
                        && notification.description().equals("Finalize seu primeiro curso.")));
  }
}
