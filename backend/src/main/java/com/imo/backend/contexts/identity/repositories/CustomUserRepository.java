package com.imo.backend.contexts.identity.repositories;

import com.imo.backend.contexts.identity.User;

public interface CustomUserRepository {
  User toggleAccessById(String id, Boolean isConfirmed);
}
