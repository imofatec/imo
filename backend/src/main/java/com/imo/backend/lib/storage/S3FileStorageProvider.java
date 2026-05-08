package com.imo.backend.lib.storage;

import com.imo.backend.contexts.identity.user.lib.ImageStorageProvider;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@ConditionalOnProperty(name = "storage.mode", havingValue = "s3")
public class S3FileStorageProvider implements ImageStorageProvider {

  private final S3Client s3Client;

  @Value("${aws.s3.bucket}")
  private String bucket;

  public S3FileStorageProvider(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  @Override
  public String store(String userId, MultipartFile file, String fileName) {

    String key = userId + "/" + fileName;

    try {
      PutObjectRequest request =
          PutObjectRequest.builder()
              .bucket(bucket)
              .key(key)
              .contentType(file.getContentType())
              .contentDisposition("inline")
              .build();

      s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

      URL url =
          s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(bucket).key(key).build());

      return url.toString();
    } catch (IOException e) {
      throw new RuntimeException("Erro ao fazer upload para S3");
    }
  }

  @Override
  public void delete(String path) {

    if (path == null || path.isEmpty()) {
      return;
    }

    String key = extractKeyFromUrl(path);

    DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucket).key(key).build();

    s3Client.deleteObject(request);
  }

  private String extractKeyFromUrl(String url) {
    try {
      URI uri = new URI(url);
      String path = uri.getPath();
      return path.startsWith("/") ? path.substring(1) : path;
    } catch (Exception e) {
      return url;
    }
  }
}
