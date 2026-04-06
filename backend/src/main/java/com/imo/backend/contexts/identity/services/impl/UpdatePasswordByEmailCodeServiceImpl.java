package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.apagar_dps.Outbox;
import com.imo.backend.contexts.apagar_dps.OutboxEvent;
import com.imo.backend.contexts.apagar_dps.OutboxStatus;
import com.imo.backend.contexts.apagar_dps.repositories.OutboxRepository;
import com.imo.backend.contexts.common.Pageable;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.events.ForgetPasswordEvent;
import com.imo.backend.contexts.identity.services.UpdatePasswordByEmailCodeService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordByEmailCodeServiceImpl implements UpdatePasswordByEmailCodeService {

  private final OutboxRepository<ForgetPasswordEvent> outboxRepository;

  private final UpdatePasswordByIdServiceImpl updatePasswordByIdServiceImpl;

  public UpdatePasswordByEmailCodeServiceImpl(
      OutboxRepository<ForgetPasswordEvent> outboxRepository,
      UpdatePasswordByIdServiceImpl updatePasswordByIdServiceImpl) {
    this.outboxRepository = outboxRepository;
    this.updatePasswordByIdServiceImpl = updatePasswordByIdServiceImpl;
  }

  public User execute(String emailCode, String userId, String newPassword) {
    var pageNumber = 0;
    var pageSize = 10;

    do {
      var pageable = Pageable.fromPageSize(pageNumber, pageSize);
      var outboxes =
          this.outboxRepository
              .findByStatusAndEvent(
                  OutboxStatus.WAITING_TO_UPDATE_PASSWORD,
                  OutboxEvent.USER_FORGET_PASSWORD,
                  pageable)
              .getContent();

      if (outboxes.isEmpty()) {
        throw new NotFoundException(String.format("Código %s inválido", emailCode));
      }

      boolean verified = this.verifyPayload(outboxes, userId, emailCode);

      if (verified) {
        return this.updatePasswordByIdServiceImpl.execute(userId, newPassword);
      }

      pageNumber++;

    } while (true);
  }

  private boolean verifyPayload(
      List<Outbox<ForgetPasswordEvent>> outboxes, String userId, String code) {
    return outboxes.stream()
        .anyMatch(
            outbox -> {
              var payload = outbox.getPayload();
              if (!payload.userId().equals(userId) || !payload.code().equals(code)) {
                return false;
              }

              var outboxesFromUser = this.outboxRepository.findByUserId(userId);

              outboxesFromUser.forEach(
                  outboxFromUser -> {
                    this.outboxRepository.deleteById(outboxFromUser.getId());
                  });
              return true;
            });
  }
}
