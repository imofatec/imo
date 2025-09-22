package com.imo.backend.modules.user.events;

import com.imo.backend.modules.outbox.Outbox;
import com.imo.backend.modules.outbox.OutboxEvent;
import com.imo.backend.modules.outbox.OutboxStatus;
import com.imo.backend.modules.outbox.services.CreateOutboxService;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
  private final CreateOutboxService<UserDTO> createUserOutboxService;

  private final CreateOutboxService<ForgetPasswordMessagePayload> createForgetPasswordOutboxService2;

  public UserEventListener(
      CreateOutboxService<UserDTO> createUserOutboxService,
      CreateOutboxService<ForgetPasswordMessagePayload> createForgetPasswordOutboxService2
  ) {
    this.createUserOutboxService = createUserOutboxService;
    this.createForgetPasswordOutboxService2 = createForgetPasswordOutboxService2;
  }

  @ApplicationModuleListener
  public void handle(SendEmailConfirmationEvent event) {
    var outbox = new Outbox<>(
        event.user(),
        OutboxStatus.PENDING,
        OutboxEvent.USER_EMAIL_CONFIRMATION
    );
    this.createUserOutboxService.execute(outbox);
  }

  @ApplicationModuleListener
  public void handle(ForgetPasswordEvent event) {
    var forgotPasswordOutbox = new ForgetPasswordMessagePayload(
        event.userId(),
        event.userEmail(),
        event.checkCode()
    );

    var newOutbox = new Outbox<>(
        forgotPasswordOutbox,
        OutboxStatus.PENDING,
        OutboxEvent.USER_FORGET_PASSWORD
    );

    this.createForgetPasswordOutboxService2.execute(newOutbox);
  }
}
