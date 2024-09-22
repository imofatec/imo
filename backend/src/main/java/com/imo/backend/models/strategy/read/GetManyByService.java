package com.imo.backend.models.strategy.read;

import java.util.List;

// E - Elements
public interface GetManyByService<E> {
    List<E> execute(String searchParam);
}
