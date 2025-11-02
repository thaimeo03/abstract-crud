package com.tht.abstract_crud.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.model.user.request.CreateUserRequest;
import com.tht.abstract_crud.repository.UserRepository;
import com.tht.abstract_crud.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User save(CreateUserRequest user) {
    User newUser = User.builder().username(user.getUsername()).build();

    return userRepository.save(newUser);
  }
}
