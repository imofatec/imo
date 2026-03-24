package com.imo.backend.contexts.identity.recovery.repositories;

import com.imo.backend.contexts.identity.recovery.RecoveryCode;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomRecoveryCodeRepository {
  Optional<RecoveryCode> findByUserId(String userId);
}
