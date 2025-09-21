package com.imo.backend.modules.outbox.services.impl;

import com.imo.backend.modules.outbox.Outbox;
import com.imo.backend.modules.outbox.repositories.OutboxRepository;
import com.imo.backend.modules.outbox.services.CreateOutboxService;
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
