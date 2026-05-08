package com.imo.backend.lib.notification;

import com.imo.backend.contexts.notification.http.dtos.AchievementUnlockedNotificationDTO;
import com.imo.backend.contexts.notification.http.dtos.ConnectedNotificationDTO;
import com.imo.backend.contexts.notification.lib.NotificationProvider;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
public class InMemorySseNotificationProvider implements NotificationProvider {
  private final ConcurrentMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  @Override
  public SseEmitter connect(String userId) {
    SseEmitter emitter = new SseEmitter(0L);
    registerEmitterLifecycle(userId, emitter);

    SseEmitter previousEmitter = this.emitters.put(userId, emitter);
    if (previousEmitter != null) {
      previousEmitter.complete();
    }

    sendEvent(
        userId, emitter, "connected", new ConnectedNotificationDTO("Conexão SSE estabelecida"));
    return emitter;
  }

  @Override
  public void sendAchievementUnlocked(
      String userId, AchievementUnlockedNotificationDTO notification) {
    SseEmitter emitter = this.emitters.get(userId);

    if (emitter == null) {
      return;
    }

    sendEvent(userId, emitter, "achievement-unlocked", notification);
  }

  private void registerEmitterLifecycle(String userId, SseEmitter emitter) {
    emitter.onCompletion(() -> removeEmitter(userId, emitter));
    emitter.onTimeout(() -> removeEmitter(userId, emitter));
    emitter.onError(ex -> removeEmitter(userId, emitter));
  }

  private void removeEmitter(String userId, SseEmitter emitter) {
    this.emitters.remove(userId, emitter);
  }

  private void sendEvent(String userId, SseEmitter emitter, String eventName, Object data) {
    try {
      emitter.send(SseEmitter.event().name(eventName).data(data));
    } catch (IOException | IllegalStateException ex) {
      log.debug("Falha ao enviar evento SSE {} para o usuário {}", eventName, userId, ex);
      removeEmitter(userId, emitter);
      emitter.completeWithError(ex);
    }
  }
}
