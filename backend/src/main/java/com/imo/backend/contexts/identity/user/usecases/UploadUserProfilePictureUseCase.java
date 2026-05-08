package com.imo.backend.contexts.identity.user.usecases;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.lib.ImageStorageProvider;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadUserProfilePictureUseCase {

  private final UserRepository userRepository;
  private final ImageStorageProvider fileStorageManager;

  public UploadUserProfilePictureUseCase(
      UserRepository userRepository, ImageStorageProvider fileStorageManager) {
    this.userRepository = userRepository;
    this.fileStorageManager = fileStorageManager;
  }

  public User execute(String id, MultipartFile file) {

    var user =
        this.userRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    String filename =
        user.assertUploadProfilePicture(
            file.getOriginalFilename(), file.getSize(), file.getContentType());

    if (user.getProfilePicturePath() != null) {
      fileStorageManager.delete(user.getProfilePicturePath());
    }

    String fileDirFromUserDir = fileStorageManager.store(user.getId(), file, filename);
    user.setProfilePicturePath(fileDirFromUserDir);
    return this.userRepository.save(user);
  }
}
