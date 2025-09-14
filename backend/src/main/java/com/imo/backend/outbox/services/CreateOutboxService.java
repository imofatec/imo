package com.imo.backend.outbox.services;

import com.imo.backend.outbox.Outbox;
import com.imo.backend.outbox.repositories.OutboxRepository;
import com.imo.backend.outbox.services.interfaces.ICreateOutboxService;
import org.springframework.stereotype.Service;

@Service
public class CreateOutboxService<T> implements ICreateOutboxService<T> {
  private final OutboxRepository<T> outboxRepository;

  public CreateOutboxService(OutboxRepository<T> outboxRepository) {
    this.outboxRepository = outboxRepository;
  }

  @Override
  public Outbox<T> execute(Outbox<T> outbox) {
    return this.outboxRepository.save(outbox);
  }
}
