package com.imo.backend.config.static_resources;

import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileManager implements WebMvcConfigurer {

  private final String uploadDir =
      Paths.get("src", "main", "resources", "uploads").toAbsolutePath().toString();

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("uploads/**").addResourceLocations("file:" + uploadDir + "/");
  }
}
