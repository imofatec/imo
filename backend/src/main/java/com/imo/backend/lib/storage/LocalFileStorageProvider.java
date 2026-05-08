package com.imo.backend.lib.storage;

import com.imo.backend.contexts.identity.user.lib.ImageStorageProvider;
import java.io.IOException;
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

  public LocalFileStorageProvider(@Value("${storage.local.upload-dir}") String uploadDir) {
    this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
  }

  @Override
  public String store(String userId, MultipartFile file, String fileName) {
    Path userDir = resolveUserDir(userId);

    try {
      Path destination = userDir.resolve(fileName);
      file.transferTo(destination);
      return userId + "/" + fileName;
    } catch (IOException e) {
      throw new RuntimeException("Erro ao processar arquivo");
    }
  }

  @Override
  public void delete(String path) {
    if (path == null || path.isEmpty()) {
      return;
    }

    String relativePath = path;

    if (relativePath.startsWith("/uploads/")) {
      relativePath = relativePath.substring("/uploads/".length());
    }

    Path filePath = uploadDir.resolve(relativePath).normalize();

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
}
