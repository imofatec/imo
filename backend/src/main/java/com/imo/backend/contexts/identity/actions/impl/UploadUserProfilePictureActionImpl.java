package com.imo.backend.contexts.identity.actions.impl;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.UploadUserProfilePictureAction;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Service
public class UploadUserProfilePictureActionImpl implements UploadUserProfilePictureAction {
  private final UserRepository userRepository;

  private final String uploadDir = Paths
      .get("src", "main", "resources", "uploads")
      .toAbsolutePath()
      .toString();

  public UploadUserProfilePictureActionImpl(
      UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id, MultipartFile file) {
    checkNullFile(file);
    checkFileSize(file);
    String filename = checkFileType(file);

    var user = this.userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    var userDir = checkUploadDir(uploadDir, id);

    try {
      File destinationFile = new File(userDir, filename);
      file.transferTo(destinationFile);

      String fileDirFromUserDir = String.format(id + "/" + filename);
      user.setProfilePicturePath(fileDirFromUserDir);
      return this.userRepository.save(user);

    } catch (IOException e) {
      throw new Error("Erro ao processar arquivo");
    }
  }

  private static void checkNullFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new BadRequestException("Adicione um arquivo");
    }
  }

  private static void checkFileSize(MultipartFile file) {
    long fileSizeInMb = file.getSize() / (1024 * 1024);
    if (fileSizeInMb > 10) {
      throw new BadRequestException("O arquivo não pode ter mais do que 10MB");
    }
  }

  private static String checkFileType(MultipartFile file) {
    if (file.getContentType() == null || file.getOriginalFilename() == null) {
      throw new BadRequestException("Arquivo corrompido");
    }

    String filename = file.getOriginalFilename();

    String regex = "image/jpg|image/jpeg|image/png";
    Pattern pattern = Pattern.compile(regex);

    if (!pattern.matcher(file.getContentType()).matches()) {
      throw new BadRequestException(String.format("O formato %s não é valido, só é válido imagens png, jpeg e jpg",
          file.getContentType()
      ));
    }

    return filename;
  }

  private static File checkUploadDir(String uploadDir, String userId) {
    File userDir = new File(uploadDir, userId);
    if (!userDir.exists()) {
      boolean createdDir = userDir.mkdir();
      if (!createdDir) {
        throw new RuntimeException("Não foi possível armazenar o arquivo");
      }
    }
    return userDir;
  }
}
