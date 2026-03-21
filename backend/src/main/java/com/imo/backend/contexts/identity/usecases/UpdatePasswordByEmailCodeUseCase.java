package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.apagar_dps.Outbox;
import com.imo.backend.contexts.apagar_dps.OutboxEvent;
import com.imo.backend.contexts.apagar_dps.OutboxStatus;
import com.imo.backend.contexts.apagar_dps.repositories.OutboxRepository;
import com.imo.backend.contexts.common.Pageable;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.events.ForgetPasswordEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdatePasswordByEmailCodeUseCase {

  private final OutboxRepository<ForgetPasswordEvent> outboxRepository;

  private final UpdatePasswordByIdUseCase updatePasswordByIdUseCase;

  public UpdatePasswordByEmailCodeUseCase(
      OutboxRepository<ForgetPasswordEvent> outboxRepository,
      UpdatePasswordByIdUseCase updatePasswordByIdUseCase
  ) {
    this.outboxRepository = outboxRepository;
    this.updatePasswordByIdUseCase = updatePasswordByIdUseCase;
  }

  public User execute(String emailCode, String userId, String newPassword) {
    var pageNumber = 0;
    var pageSize = 10;

    do {
      var pageable = Pageable.fromPageSize(pageNumber, pageSize);
      var outboxes = this.outboxRepository
          .findByStatusAndEvent(
              OutboxStatus.WAITING_TO_UPDATE_PASSWORD,
              OutboxEvent.USER_FORGET_PASSWORD,
              pageable
          )
          .getContent();

      if (outboxes.isEmpty()) {
        throw new NotFoundException(String.format("Código %s inválido", emailCode));
      }

      boolean verified = this.verifyPayload(outboxes, userId, emailCode);

      if (verified) {
        return this.updatePasswordByIdUseCase.execute(userId, newPassword);
      }

      pageNumber++;

    } while (true);
  }


  private boolean verifyPayload(
      List<Outbox<ForgetPasswordEvent>> outboxes,
      String userId,
      String code
  ) {
    return outboxes.stream().anyMatch(outbox -> {
      var payload = outbox.getPayload();
      if (!payload.userId().equals(userId) || !payload.code().equals(code)) {
        return false;
      }

      var outboxesFromUser = this.outboxRepository.findByUserId(userId);

      outboxesFromUser.forEach(outboxFromUser -> {
        this.outboxRepository.deleteById(outboxFromUser.getId());
      });
      return true;
    });
  }
}
