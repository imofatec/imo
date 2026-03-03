package com.imo.backend.contexts.identity.guards;

import com.imo.backend.contexts.identity.User;

public interface GetUserByIdGuard {
  User execute(String id);
}
