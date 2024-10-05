package com.imo.backend.models.strategy.get.one;

// T - Type
public interface GetOneByService<T> {
    T execute(String searchParam);
}
