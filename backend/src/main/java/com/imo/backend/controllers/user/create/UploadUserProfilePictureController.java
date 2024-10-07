package com.imo.backend.controllers.user.create;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.services.update.UploadUserProfilePictureService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadUserProfilePictureController extends UserController {

    private final UploadUserProfilePictureService uploadUserProfilePictureService;

    public UploadUserProfilePictureController(UploadUserProfilePictureService uploadUserProfilePictureService) {
        this.uploadUserProfilePictureService = uploadUserProfilePictureService;
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping(value = "upload/profile-pic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NoPasswordUser> handle(
            @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        var updatedUser = uploadUserProfilePictureService.execute(file, request.getHeader("Authorization"));
        return ResponseEntity.ok(updatedUser);
    }
}
