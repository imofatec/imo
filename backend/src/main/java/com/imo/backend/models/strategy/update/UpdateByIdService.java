package com.imo.backend.models.strategy.update;

public interface UpdateByIdService<T, R> {
    R execute(String id, T dto, String token);
}
