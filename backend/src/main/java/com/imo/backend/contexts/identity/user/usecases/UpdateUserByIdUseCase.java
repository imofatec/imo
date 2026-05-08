package com.imo.backend.contexts.identity.user.usecases;

import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.UserPolicies;
import com.imo.backend.contexts.identity.user.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserByIdUseCase {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final UserPolicies userPolicies;

  public UpdateUserByIdUseCase(
      PasswordEncoder passwordEncoder, UserRepository userRepository, UserPolicies userPolicies) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.userPolicies = userPolicies;
  }

  public User execute(String id, UpdateUserByIdCommand cmd) {
    var foundUser = userRepository.findById(id).orElse(null);

    if (foundUser == null) {
      return null;
    }

    if (cmd.name() != null && !cmd.name().isEmpty()) {
      foundUser.setName(cmd.name());
    }

    if (cmd.email() != null && !cmd.email().isEmpty()) {
      this.userPolicies.assertEmailAvailable(cmd.email(), foundUser.getId());
      foundUser.setEmail(cmd.email());
    }

    if (cmd.bio() != null) {
      foundUser.setBio(cmd.bio());
    }

    if (cmd.password() != null && !cmd.password().isEmpty()) {
      this.userPolicies.assertCurrentPassword(foundUser, cmd.oldPassword());
      foundUser.setPassword(this.passwordEncoder.encode(cmd.password()));
    }

    if (cmd.profilePicturePath() != null && !cmd.profilePicturePath().isEmpty()) {
      foundUser.setProfilePicturePath(cmd.profilePicturePath());
    }

    if (cmd.birthDate() != null) {
      foundUser.setBirthDate(cmd.birthDate());
    }

    if (cmd.availableTimePerDay() != null) {
      foundUser.setAvailableTimePerDay(cmd.availableTimePerDay());
    }

    if (cmd.academicDegree() != null) {
      foundUser.setAcademicDegree(cmd.academicDegree());
    }

    if (cmd.experienceLevel() != null) {
      foundUser.setExperienceLevel(cmd.experienceLevel());
    }

    if (cmd.categoriesOfInterest() != null) {
      foundUser.setCategoriesOfInterest(cmd.categoriesOfInterest());
    }

    return this.userRepository.save(foundUser);
  }
}
