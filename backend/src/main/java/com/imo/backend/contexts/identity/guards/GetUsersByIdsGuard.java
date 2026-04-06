package com.imo.backend.contexts.identity.guards;

import com.imo.backend.contexts.identity.User;
import java.util.List;

public interface GetUsersByIdsGuard {
  List<User> execute(List<String> ids);
}
