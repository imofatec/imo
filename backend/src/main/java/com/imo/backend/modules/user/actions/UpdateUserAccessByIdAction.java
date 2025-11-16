package com.imo.backend.modules.user.actions;

import com.imo.backend.modules.user.User;

public interface UpdateUserAccessByIdAction {
  User execute(String id);
}
