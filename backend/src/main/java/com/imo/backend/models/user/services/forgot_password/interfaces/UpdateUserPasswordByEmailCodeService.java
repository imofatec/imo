package com.imo.backend.models.user.services.forgot_password.interfaces;

import com.imo.backend.models.user.dtos.NoPasswordUser;

public interface UpdateUserPasswordByEmailCodeService {
  NoPasswordUser execute(String emailCode, String userId, String newPassword);
}
