package com.imo.backend.models.user.services.update.interfaces;

import com.imo.backend.models.user.dtos.NoPasswordUser;

public interface IUpdateAccessUserService {
    NoPasswordUser execute(String token);
}
