package com.imo.backend.contexts.apagar_dps;

import com.imo.backend.contexts.common.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "outbox")
@AllArgsConstructor
@Data
public class Outbox<T> extends Entity {
  private T payload;

  private OutboxStatus status;

  private OutboxEvent event;
}
