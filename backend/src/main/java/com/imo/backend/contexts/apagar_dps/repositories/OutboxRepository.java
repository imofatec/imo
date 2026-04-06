package com.imo.backend.contexts.apagar_dps.repositories;

import com.imo.backend.contexts.apagar_dps.Outbox;
import com.imo.backend.contexts.apagar_dps.OutboxEvent;
import com.imo.backend.contexts.apagar_dps.OutboxStatus;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface OutboxRepository<T> extends MongoRepository<Outbox<T>, String> {
  Page<Outbox<T>> findByStatusAndEvent(OutboxStatus status, OutboxEvent event, Pageable pageable);

  @Query("{'payload.userId':  ?0}")
  List<Outbox<T>> findByUserId(String userId);

  @Query("{'_id' : ?0}")
  @Update("{ $set: ?1 }")
  void updateById(String id, Map<String, ?> fieldToUpdate);
}
