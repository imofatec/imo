package com.imo.backend.contexts.common;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUrlHelper {
  private final Environment environment;

  public ApplicationUrlHelper(Environment environment) {
    this.environment = environment;
  }

  public String getBackendBaseUrl() {
    String configuredBaseUrl = this.environment.getProperty("backend.base-url");

    if (configuredBaseUrl != null && !configuredBaseUrl.isBlank()) {
      return normalizeBaseUrl(configuredBaseUrl);
    }

    String port = this.environment.getProperty("local.server.port");

    if (port == null || port.isBlank()) {
      port = this.environment.getProperty("server.port");
    }

    if (port == null || port.isBlank() || "0".equals(port.trim())) {
      throw new BadRequestException(
          "Configure BACKEND_BASE_URL ou backend.base-url para montar URLs absolutas do backend");
    }

    String host = this.environment.getProperty("server.address", "localhost");

    if ("0.0.0.0".equals(host)) {
      host = "localhost";
    }

    return normalizeBaseUrl("http://" + host + ":" + port.trim());
  }

  public String getFrontendClientUrl() {
    String frontendClientUrl = this.environment.getProperty("frontend.client.url");

    if (frontendClientUrl == null || frontendClientUrl.isBlank()) {
      throw new BadRequestException(
          "Configure CLIENT_URL ou frontend.client.url para montar URLs do frontend");
    }

    return normalizeBaseUrl(frontendClientUrl);
  }

  public String buildBackendUrl(String path) {
    return this.getBackendBaseUrl() + normalizePath(path);
  }

  public String buildFrontendUrl(String path) {
    return this.getFrontendClientUrl() + normalizePath(path);
  }

  private static String normalizeBaseUrl(String baseUrl) {
    String normalizedBaseUrl = baseUrl.trim();

    if (normalizedBaseUrl.endsWith("/")) {
      normalizedBaseUrl = normalizedBaseUrl.substring(0, normalizedBaseUrl.length() - 1);
    }

    return normalizedBaseUrl;
  }

  private static String normalizePath(String path) {
    if (path == null || path.isBlank()) {
      return "";
    }

    return path.startsWith("/") ? path : "/" + path;
  }
}
