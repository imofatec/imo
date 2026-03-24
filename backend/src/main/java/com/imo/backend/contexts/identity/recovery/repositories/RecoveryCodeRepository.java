package com.imo.backend.contexts.identity.recovery.repositories;

import com.imo.backend.contexts.identity.recovery.RecoveryCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryCodeRepository
    extends MongoRepository<RecoveryCode, String>, CustomRecoveryCodeRepository {
}
