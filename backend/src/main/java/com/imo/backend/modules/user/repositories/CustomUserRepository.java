package com.imo.backend.modules.user.repositories;

import com.imo.backend.modules.user.User;

public interface CustomUserRepository {
  User toggleAccessById(String id, Boolean isConfirmed);
}
