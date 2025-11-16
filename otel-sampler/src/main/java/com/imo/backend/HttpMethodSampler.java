package com.imo.backend;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.sdk.trace.samplers.SamplingResult;
import io.opentelemetry.semconv.SemanticAttributes;

import java.util.List;

public class HttpMethodSampler implements Sampler {

  private final Sampler rootSampler;

  public HttpMethodSampler(Sampler rootSampler) {
    this.rootSampler = rootSampler;
  }

  @Override
  public SamplingResult shouldSample(
      Context parentContext,
      String traceId,
      String name,
      SpanKind spanKind,
      Attributes attributes,
      List<LinkData> parentLinks
  ) {

    String httpMethod = getHttpMethod(attributes);

    if (("GET".equalsIgnoreCase(httpMethod) || "HEAD".equalsIgnoreCase(httpMethod))
        && spanKind == SpanKind.SERVER) {
      return SamplingResult.drop();
    }

    return rootSampler.shouldSample(
        parentContext,
        traceId,
        name,
        spanKind,
        attributes,
        parentLinks
    );
  }

  private String getHttpMethod(Attributes attributes) {
    String method = attributes.get(SemanticAttributes.HTTP_REQUEST_METHOD);

    if (method == null) {
      method = attributes.get(AttributeKey.stringKey("http.method"));
    }

    return method;
  }

  @Override
  public String getDescription() {
    return "HttpMethodSampler{excludes GET requests, delegates to "
           + rootSampler.getDescription()
           + "}";
  }
}
