package com.imo.backend.config.static_resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileManager implements WebMvcConfigurer {
  private static final String ACHIEVEMENTS_DIR = "classpath:/static/images/achievements/";

  @Value("${storage.local.upload-dir}")
  private String uploadDir;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadDir + "/");
    registry.addResourceHandler("/images/achievements/**").addResourceLocations(ACHIEVEMENTS_DIR);
  }
}
