package com.imo.backend.models.strategy.read;

// T - Type
public interface GetOneByService<T> {
    T execute(String searchParam);
}
