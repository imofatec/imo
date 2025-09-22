package com.imo.backend.modules.user.http.controllers.update;

import com.imo.backend.modules.user.actions.UploadUserProfilePictureAction;
import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.lib.token.TokenManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadUserProfilePictureController extends UserController {

  private final UploadUserProfilePictureAction uploadUserProfilePictureAction;

  public UploadUserProfilePictureController(
      UploadUserProfilePictureAction uploadUserProfilePictureAction,
      TokenManager tokenManager
  ) {
    this.uploadUserProfilePictureAction = uploadUserProfilePictureAction;
  }

  @Operation(summary = "Upload a profile picture", description = "Allow users to upload a new profile picture")
  @SecurityRequirement(name = "Authorization")
  @PutMapping(value = "/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UserDTO> handle(
      @RequestParam("file")
      MultipartFile file
  ) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    var updatedUser = UserDTO.fromUser(this.uploadUserProfilePictureAction.execute(userId, file));

    return ResponseEntity.ok(updatedUser);
  }
}
