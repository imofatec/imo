package com.imo.backend;

import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSamplerProvider;
import io.opentelemetry.sdk.trace.samplers.Sampler;

public class HttpMethodSamplerProvider implements ConfigurableSamplerProvider {

  @Override
  public Sampler createSampler(ConfigProperties config) {
    return new HttpMethodSampler(Sampler.alwaysOn());
  }

  @Override
  public String getName() {
    return "http_method_filter";
  }
}
