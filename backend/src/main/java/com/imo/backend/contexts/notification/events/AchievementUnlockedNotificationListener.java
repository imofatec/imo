package com.imo.backend.contexts.notification.events;

import com.imo.backend.contexts.notification.http.dtos.AchievementUnlockedNotificationDTO;
import com.imo.backend.contexts.notification.lib.NotificationProvider;
import com.imo.backend.contexts.recognition.events.AchievementUnlockedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AchievementUnlockedNotificationListener {
  private final NotificationProvider notificationProvider;

  public AchievementUnlockedNotificationListener(NotificationProvider notificationProvider) {
    this.notificationProvider = notificationProvider;
  }

  @Async
  @EventListener
  public void handle(AchievementUnlockedEvent event) {
    this.notificationProvider.sendAchievementUnlocked(
        event.userId(), AchievementUnlockedNotificationDTO.fromAchievement(event.achievement()));
  }
}
