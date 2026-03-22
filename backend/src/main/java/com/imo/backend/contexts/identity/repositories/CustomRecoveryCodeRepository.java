package com.imo.backend.contexts.identity.repositories;

import com.imo.backend.contexts.identity.RecoveryCode;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomRecoveryCodeRepository {
  Optional<RecoveryCode> findByUserId(String userId);
}
