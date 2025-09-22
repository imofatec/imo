package com.imo.backend.modules.progress.repositories;

import com.imo.backend.modules.progress.Progress;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgressRepository
    extends MongoRepository<Progress, String>, CustomProgressRepository {
}
