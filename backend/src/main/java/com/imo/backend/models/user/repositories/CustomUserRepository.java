package com.imo.backend.models.user.repositories;

import com.imo.backend.models.user.dtos.FieldsToUpdateUser;

public interface CustomUserRepository {
    void updateUser(String id, FieldsToUpdateUser fieldsToUpdateUser);
}
