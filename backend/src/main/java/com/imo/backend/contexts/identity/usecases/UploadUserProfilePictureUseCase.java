package com.imo.backend.contexts.identity.usecases;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.repositories.UserRepository;

@Service
public class UploadUserProfilePictureUseCase {

    private final UserRepository userRepository;

    private final String uploadDir = Paths
            .get("src", "main", "resources", "uploads")
            .toAbsolutePath()
            .toString();

    public UploadUserProfilePictureUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String id, MultipartFile file) {

        var user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        String filename = user.assertUploadProfilePicture(file);

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
