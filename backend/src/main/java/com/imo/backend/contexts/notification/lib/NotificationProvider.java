package com.imo.backend.contexts.notification.lib;

import com.imo.backend.contexts.notification.http.dtos.AchievementUnlockedNotificationDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationProvider {
  SseEmitter connect(String userId);

  void sendAchievementUnlocked(String userId, AchievementUnlockedNotificationDTO notification);
}
