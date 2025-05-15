package com.imo.backend.models.user.services.get.interfaces;

import com.imo.backend.models.user.dtos.NoPasswordUser;

public interface GetUserByIdService {
  NoPasswordUser execute(String id);
}
