package com.imo.backend.contexts.identity.repositories;

import com.imo.backend.contexts.identity.RecoveryCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryCodeRepository
    extends MongoRepository<RecoveryCode, String>, CustomRecoveryCodeRepository {
}
