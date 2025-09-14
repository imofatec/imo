package com.imo.backend.modules.user.guards;

import com.imo.backend.modules.user.User;

import java.util.List;

public interface GetUsersByIdsGuard {
  List<User> execute(List<String> ids);
}
