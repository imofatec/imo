package com.imo.backend.models.strategy.get.many.to_set;

import java.util.Set;

// E - Elements
public interface GetManyToSetService<E> {
    Set<E> execute();
}
