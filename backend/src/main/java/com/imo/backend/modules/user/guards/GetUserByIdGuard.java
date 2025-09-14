package com.imo.backend.modules.user.guards;

import com.imo.backend.modules.user.User;

public interface GetUserByIdGuard {
  User execute(String id);
}
