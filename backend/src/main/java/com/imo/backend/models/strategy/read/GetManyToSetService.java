package com.imo.backend.models.strategy.read;

import java.util.Set;

// E - Elements
public interface GetManyToSetService<E> {
    Set<E> execute();
}
