package com.imo.backend.outbox;

import com.imo.backend.modules.base.Entity;
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
