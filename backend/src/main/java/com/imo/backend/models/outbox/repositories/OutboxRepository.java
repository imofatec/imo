package com.imo.backend.models.outbox.repositories;

import com.imo.backend.models.outbox.Outbox;
import com.imo.backend.models.outbox.OutboxEvent;
import com.imo.backend.models.outbox.OutboxStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Map;

public interface OutboxRepository<T> extends MongoRepository<Outbox<T>, String> {
  Page<Outbox<T>> findByStatusAndEvent(OutboxStatus status, OutboxEvent event, Pageable pageable);

  @Query("{'_id' : ?0}")
  @Update("{ $set: ?1 }")
  void updateById(String id, Map<String, ?> fieldToUpdate);
}
