package com.imo.backend.models.strategy.read;

import java.util.List;

// E - Elements
public interface GetManyService<E>  {
    List<E> execute();
}
