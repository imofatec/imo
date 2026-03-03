package com.imo.backend.contexts.apagar_dps.services.impl;

import com.imo.backend.contexts.apagar_dps.Outbox;
import com.imo.backend.contexts.apagar_dps.repositories.OutboxRepository;
import com.imo.backend.contexts.apagar_dps.services.CreateOutboxService;
import org.springframework.stereotype.Service;

@Service
public class CreateOutboxServiceImpl<T> implements CreateOutboxService<T> {
  private final OutboxRepository<T> outboxRepository;

  public CreateOutboxServiceImpl(OutboxRepository<T> outboxRepository) {
    this.outboxRepository = outboxRepository;
  }

  @Override
  public Outbox<T> execute(Outbox<T> outbox) {
    return this.outboxRepository.save(outbox);
  }
}
