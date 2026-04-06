package com.imo.backend.contexts.journey_tracking.repositories;

import com.imo.backend.contexts.journey_tracking.Progress;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgressRepository
    extends MongoRepository<Progress, String>, CustomProgressRepository {}
