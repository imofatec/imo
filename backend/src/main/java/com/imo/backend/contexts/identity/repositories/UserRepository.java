package com.imo.backend.contexts.identity.repositories;

import com.imo.backend.contexts.identity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {
  Optional<User> findByEmail(String email);

  @Query("{ '_id': { $in: ?0 } }")
  List<User> findByIds(List<String> ids);
}
