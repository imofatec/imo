package com.imo.backend.models.strategy.create;

// Interface com generics
// T - Type
// R - Response
public interface CreateService<T, R> {
    R execute(T dto);
}
