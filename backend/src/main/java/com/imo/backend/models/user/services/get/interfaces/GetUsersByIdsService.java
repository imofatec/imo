package com.imo.backend.models.user.services.get.interfaces;

import com.imo.backend.models.user.dtos.NoPasswordUser;

import java.util.List;

public interface GetUsersByIdsService {
  List<NoPasswordUser> execute(List<String> ids);
}
