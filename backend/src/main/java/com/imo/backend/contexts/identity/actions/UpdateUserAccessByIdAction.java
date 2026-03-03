package com.imo.backend.contexts.identity.actions;

import com.imo.backend.contexts.identity.User;

public interface UpdateUserAccessByIdAction {
  User execute(String id);
}
