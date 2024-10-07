package com.imo.backend.models.user.services.update;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.BadRequestException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.strategy.create.CreateWithTokenService;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Service
public class UploadUserProfilePictureService implements CreateWithTokenService<MultipartFile, NoPasswordUser> {
    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final String uploadDir = Paths.get("src", "main", "resources", "uploads").toAbsolutePath().toString();

    public UploadUserProfilePictureService(
            UserRepository userRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public NoPasswordUser execute(MultipartFile file, String token) {
        checkNullFile(file);
        checkFileSize(file);
        String filename = checkFileType(file);

        var userId = tokenService.getSub(token).get("id");
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        var userDir = checkUploadDir(uploadDir, userId);

        try {
            File destinationFile = new File(userDir, filename);
            file.transferTo(destinationFile);

            String fileDirFromUserDir = String.format(userId + "/" + filename);
            userRepository.updateProfilePictureById(userId, fileDirFromUserDir);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }

        var updatedUser = userRepository.findById(userId);
        assert updatedUser.isPresent();

        return NoPasswordUser.fromUser(updatedUser.get());
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
            throw new BadRequestException(String.format("O formato %s não é valido, só é válido imagens png, jpeg e jpg", file.getContentType()));
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
