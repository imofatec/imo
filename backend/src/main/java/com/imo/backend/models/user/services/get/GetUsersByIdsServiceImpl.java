package com.imo.backend.models.user.services.get;

import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.services.get.interfaces.GetUsersByIdsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUsersByIdsServiceImpl implements GetUsersByIdsService {
  private final UserRepository userRepository;

  public GetUsersByIdsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<NoPasswordUser> execute(List<String> ids) {
    return userRepository.findByIds(ids).stream()
        .map(NoPasswordUser::fromUser)
        .toList();
  }
}
