package com.imo.backend.models.outbox.repositories;

import com.imo.backend.models.outbox.Outbox;
import com.imo.backend.models.outbox.OutboxEvent;
import com.imo.backend.models.outbox.OutboxStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutboxRepository<T> extends MongoRepository<Outbox<T>, String> {
  Page<Outbox<T>> findByStatusAndEvent(OutboxStatus status, OutboxEvent event, Pageable pageable);
}
