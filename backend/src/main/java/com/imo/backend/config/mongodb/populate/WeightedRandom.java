package com.imo.backend.config.mongodb.populate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRandom<T> {
  private final List<T> items;
  private final List<Double> cumulativeWeights;
  private final Random random = new Random();

  public WeightedRandom(List<T> items, List<Double> weights) {
    if (items.size() != weights.size()) {
      throw new IllegalArgumentException("Itens e pesos devem ter o mesmo tamanho");
    }

    this.items = new ArrayList<>(items);
    this.cumulativeWeights = new ArrayList<>();

    double sum = 0.0;
    for (Double weight : weights) {
      sum += weight;
      this.cumulativeWeights.add(sum);
    }
  }

  public T next() {
    double total = cumulativeWeights.get(cumulativeWeights.size() - 1);
    double value = random.nextDouble() * total;

    for (int i = 0; i < cumulativeWeights.size(); i++) {
      if (value < cumulativeWeights.get(i)) {
        return items.get(i);
      }
    }

    return items.get(items.size() - 1);
  }
}
