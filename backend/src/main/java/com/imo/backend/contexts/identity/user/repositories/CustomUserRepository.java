package com.imo.backend.contexts.identity.user.repositories;

import com.imo.backend.contexts.identity.user.User;

public interface CustomUserRepository {
  User toggleAccessById(String id, Boolean isConfirmed);
}
