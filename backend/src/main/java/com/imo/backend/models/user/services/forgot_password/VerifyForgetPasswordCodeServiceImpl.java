package com.imo.backend.models.user.services.forgot_password;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.lib.Pageable;
import com.imo.backend.models.outbox.Outbox;
import com.imo.backend.models.outbox.OutboxEvent;
import com.imo.backend.models.outbox.OutboxStatus;
import com.imo.backend.models.outbox.repositories.OutboxRepository;
import com.imo.backend.models.user.dtos.ForgetPassword;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.services.forgot_password.interfaces.VerifyForgetPasswordCodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyForgetPasswordCodeServiceImpl implements VerifyForgetPasswordCodeService {
  private final UserRepository userRepository;

  private final OutboxRepository<ForgetPassword> outboxRepository;

  public VerifyForgetPasswordCodeServiceImpl(
      UserRepository userRepository,
      OutboxRepository<ForgetPassword> outboxRepository
  ) {
    this.userRepository = userRepository;
    this.outboxRepository = outboxRepository;
  }

  public NoPasswordUser execute(String userId, String code) {
    var foundUser = this.userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(String.format("Usuário %s não encontrado", userId)));

    var pageNumber = 0;
    var pageSize = 1;

    do {
      var pageable = Pageable.fromPageSize(pageNumber, pageSize);
      var outboxes = this.outboxRepository.findByStatusAndEvent(
          OutboxStatus.SENT,
          OutboxEvent.USER_FORGET_PASSWORD,
          pageable
      ).getContent();

      boolean verified = this.verifyPayload(outboxes, userId, code);

      if (verified) {
        return NoPasswordUser.fromUser(foundUser);
      }

      if (outboxes.isEmpty()) {
        throw new NotFoundException(String.format("Código %s inválido", code));
      }

      pageNumber++;
    }
    while (true);
  }

  private boolean verifyPayload(
      List<Outbox<ForgetPassword>> outboxes,
      String userId,
      String code) {
    return outboxes.stream()
        .anyMatch(outbox -> {
          var payload = outbox.getPayload();
          if (!payload.userId().equals(userId)
              || !payload.code().equals(code)) {
            return false;
          }

          this.outboxRepository.deleteById(outbox.getId());
          return true;
        });
  }
}
