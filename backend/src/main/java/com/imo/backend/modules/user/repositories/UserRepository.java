package com.imo.backend.modules.user.repositories;

import com.imo.backend.modules.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {
  Optional<User> findByEmail(String email);

  @Query("{ '_id': { $in: ?0 } }")
  List<User> findByIds(List<String> ids);
}
