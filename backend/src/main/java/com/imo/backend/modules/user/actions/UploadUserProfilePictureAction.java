package com.imo.backend.modules.user.actions;

import com.imo.backend.modules.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface UploadUserProfilePictureAction {
  User execute(String id, MultipartFile multipartFile);
}
