package com.imo.backend.contexts.identity.recovery.repositories;

import com.imo.backend.contexts.identity.recovery.RecoveryCode;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRecoveryCodeRepository {
  Optional<RecoveryCode> findValidByUserId(String userId);
}
