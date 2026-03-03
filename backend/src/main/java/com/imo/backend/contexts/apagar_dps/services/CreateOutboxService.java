package com.imo.backend.contexts.apagar_dps.services;

import com.imo.backend.contexts.apagar_dps.Outbox;

public interface CreateOutboxService<T> {
  Outbox<T> execute(Outbox<T> outbox);
}
