package com.imo.backend.models.strategy.create;

// T - Type
// R - Response
public interface CreateWithTokenService<T, R> {
    R execute(T dto, String token);
}
