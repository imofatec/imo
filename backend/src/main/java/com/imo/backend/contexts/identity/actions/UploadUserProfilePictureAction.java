package com.imo.backend.contexts.identity.actions;

import com.imo.backend.contexts.identity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UploadUserProfilePictureAction {
  User execute(String id, MultipartFile multipartFile);
}
