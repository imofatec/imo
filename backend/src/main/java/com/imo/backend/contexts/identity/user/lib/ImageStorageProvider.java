package com.imo.backend.contexts.identity.user.lib;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageProvider {
    String store(String userId, MultipartFile file, String fileName);
    void delete(String path);
}
