package com.imo.backend.outbox.services.interfaces;

import com.imo.backend.outbox.Outbox;

public interface ICreateOutboxService<T> {
  Outbox<T> execute(Outbox<T> outbox);
}
