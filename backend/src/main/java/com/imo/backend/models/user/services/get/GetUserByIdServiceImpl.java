package com.imo.backend.models.user.services.get;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.services.get.interfaces.GetUserByIdService;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdServiceImpl implements GetUserByIdService {
  private final UserRepository userRepository;

  public GetUserByIdServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public NoPasswordUser execute(String id) {
    var user = this.userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format("Usuário %s não encontrado", id)));

    return NoPasswordUser.fromUser(user);
  }
}
