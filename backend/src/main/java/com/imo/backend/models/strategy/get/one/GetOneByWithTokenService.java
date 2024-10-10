package com.imo.backend.models.strategy.get.one;

public interface GetOneByWithTokenService<T> {
    T execute(String filter, String token);
}
