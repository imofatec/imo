package com.imo.backend.lib.storage;

import com.imo.backend.contexts.common.ApplicationUrlHelper;
import com.imo.backend.contexts.identity.user.lib.ImageStorageProvider;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@ConditionalOnProperty(name = "storage.mode", havingValue = "local", matchIfMissing = true)
public class LocalFileStorageProvider implements ImageStorageProvider {

  private final Path uploadDir;
  private final ApplicationUrlHelper applicationUrlHelper;

  public LocalFileStorageProvider(
      @Value("${storage.local.upload-dir}") String uploadDir,
      ApplicationUrlHelper applicationUrlHelper) {
    this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    this.applicationUrlHelper = applicationUrlHelper;
  }

  @Override
  public String store(String userId, MultipartFile file, String fileName) {
    Path userDir = resolveUserDir(userId);

    try {
      Path destination = userDir.resolve(fileName);
      file.transferTo(destination);
      return this.applicationUrlHelper.buildBackendUrl("/uploads/" + userId + "/" + fileName);
    } catch (IOException e) {
      throw new RuntimeException("Erro ao processar arquivo");
    }
  }

  @Override
  public void delete(String path) {
    if (path == null || path.isEmpty()) {
      return;
    }

    Path filePath = uploadDir.resolve(this.extractRelativePath(path)).normalize();

    try {
      Files.deleteIfExists(filePath);
    } catch (IOException e) {
      throw new RuntimeException("Erro ao remover arquivo");
    }
  }

  private Path resolveUserDir(String userId) {
    Path userDir = uploadDir.resolve(userId).normalize();

    try {
      Files.createDirectories(userDir);
    } catch (IOException e) {
      throw new RuntimeException("Erro ao criar diretório do usuário");
    }

    return userDir;
  }

  private String extractRelativePath(String path) {
    String relativePath = URI.create(path).getPath();

    if (relativePath.startsWith("/uploads/")) {
      return relativePath.substring("/uploads/".length());
    }

    return relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
  }
}
