package com.imo.backend.models.user.services.forgot_password;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.lib.Pageable;
import com.imo.backend.models.outbox.Outbox;
import com.imo.backend.models.outbox.OutboxEvent;
import com.imo.backend.models.outbox.OutboxStatus;
import com.imo.backend.models.outbox.repositories.OutboxRepository;
import com.imo.backend.models.user.dtos.FieldsToUpdateUser;
import com.imo.backend.models.user.dtos.ForgetPassword;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.services.forgot_password.interfaces.UpdateUserPasswordByEmailCodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateUserPasswordByEmailCodeServiceImpl
    implements UpdateUserPasswordByEmailCodeService {
  private final UserRepository userRepository;

  private final OutboxRepository<ForgetPassword> outboxRepository;

  public UpdateUserPasswordByEmailCodeServiceImpl(UserRepository userRepository,
      OutboxRepository<ForgetPassword> outboxRepository
  ) {
    this.userRepository = userRepository;
    this.outboxRepository = outboxRepository;
  }

  @Override
  public NoPasswordUser execute(String emailCode, String userId, String newPassword) {
    var foundUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    var pageNumber = 0;
    var pageSize = 10;

    do {

      var pageable = Pageable.fromPageSize(pageNumber, pageSize);
      var outboxes = this.outboxRepository.findByStatusAndEvent(
          OutboxStatus.WAITING_TO_UPDATE_PASSWORD,
          OutboxEvent.USER_FORGET_PASSWORD,
          pageable
      ).getContent();

      if (outboxes.isEmpty()) {
        throw new NotFoundException(String.format("Código %s inválido", emailCode));
      }

      boolean verified = this.verifyPayload(outboxes, userId, emailCode);

      if (verified) {
        FieldsToUpdateUser fieldsToUpdateUser = new FieldsToUpdateUser();
        fieldsToUpdateUser.setPassword(newPassword);
        this.userRepository.updateUser(foundUser.getId(), fieldsToUpdateUser);
        var updatedUser = this.userRepository.findById(foundUser.getId()).get();
        return NoPasswordUser.fromUser(updatedUser);
      }

      pageNumber++;

    } while (true);
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
