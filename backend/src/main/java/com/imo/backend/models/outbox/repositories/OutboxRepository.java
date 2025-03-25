package com.imo.backend.models.outbox.repositories;

import com.imo.backend.models.outbox.Outbox;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutboxRepository<T> extends MongoRepository<Outbox<T>, String> {
}
