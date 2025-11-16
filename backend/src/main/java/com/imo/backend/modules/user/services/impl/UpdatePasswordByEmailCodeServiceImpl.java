package com.imo.backend.modules.user.services.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.outbox.Outbox;
import com.imo.backend.modules.outbox.OutboxEvent;
import com.imo.backend.modules.outbox.OutboxStatus;
import com.imo.backend.modules.outbox.repositories.OutboxRepository;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.events.ForgetPasswordEvent;
import com.imo.backend.modules.user.services.UpdatePasswordByEmailCodeService;
import com.imo.backend.utils.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdatePasswordByEmailCodeServiceImpl implements UpdatePasswordByEmailCodeService {

  private final OutboxRepository<ForgetPasswordEvent> outboxRepository;

  private final UpdatePasswordByIdServiceImpl updatePasswordByIdServiceImpl;

  public UpdatePasswordByEmailCodeServiceImpl(
      OutboxRepository<ForgetPasswordEvent> outboxRepository,
      UpdatePasswordByIdServiceImpl updatePasswordByIdServiceImpl
  ) {
    this.outboxRepository = outboxRepository;
    this.updatePasswordByIdServiceImpl = updatePasswordByIdServiceImpl;
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
        return this.updatePasswordByIdServiceImpl.execute(userId, newPassword);
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
