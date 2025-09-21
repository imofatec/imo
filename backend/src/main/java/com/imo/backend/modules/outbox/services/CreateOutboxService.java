package com.imo.backend.modules.outbox.services;

import com.imo.backend.modules.outbox.Outbox;

public interface CreateOutboxService<T> {
  Outbox<T> execute(Outbox<T> outbox);
}
