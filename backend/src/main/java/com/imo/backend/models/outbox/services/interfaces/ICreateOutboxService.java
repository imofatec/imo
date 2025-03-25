package com.imo.backend.models.outbox.services.interfaces;

import com.imo.backend.models.outbox.Outbox;

public interface ICreateOutboxService<T> {
  Outbox<T> execute(Outbox<T> outbox);
}
