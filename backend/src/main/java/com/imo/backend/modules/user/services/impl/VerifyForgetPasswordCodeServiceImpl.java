package com.imo.backend.modules.user.services.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.outbox.Outbox;
import com.imo.backend.modules.outbox.OutboxEvent;
import com.imo.backend.modules.outbox.OutboxStatus;
import com.imo.backend.modules.outbox.repositories.OutboxRepository;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.events.ForgetPasswordEvent;
import com.imo.backend.modules.user.guards.GetUserByIdGuard;
import com.imo.backend.modules.user.services.VerifyForgetPasswordCodeService;
import com.imo.backend.utils.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VerifyForgetPasswordCodeServiceImpl implements VerifyForgetPasswordCodeService {
  private final GetUserByIdGuard getUserByIdGuard;

  private final OutboxRepository<ForgetPasswordEvent> outboxRepository;

  public VerifyForgetPasswordCodeServiceImpl(
      GetUserByIdGuard getUserByIdGuard,
      OutboxRepository<ForgetPasswordEvent> outboxRepository
  ) {
    this.getUserByIdGuard = getUserByIdGuard;
    this.outboxRepository = outboxRepository;
  }

  public User execute(String userId, String code) {
    var foundUser = this.getUserByIdGuard.execute(userId);

    var pageNumber = 0;
    var pageSize = 10;

    do {
      var pageable = Pageable.fromPageSize(pageNumber, pageSize);
      var outboxes = this.outboxRepository
          .findByStatusAndEvent(OutboxStatus.SENT, OutboxEvent.USER_FORGET_PASSWORD, pageable)
          .getContent();

      if (outboxes.isEmpty()) {
        throw new NotFoundException(String.format("Código %s inválido", code));
      }

      boolean verified = this.verifyPayload(outboxes, userId, code);

      if (verified) {
        return foundUser;
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

      this.outboxRepository.updateById(
          outbox.getId(),
          Map.of("status", OutboxStatus.WAITING_TO_UPDATE_PASSWORD)
      );
      return true;
    });
  }
}
