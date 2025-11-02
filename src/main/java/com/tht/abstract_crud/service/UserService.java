package com.tht.abstract_crud.service;

import java.util.Optional;

import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.model.user.request.CreateUserRequest;

public interface UserService {
  Optional<User> findByUsername(String username);

  User save(CreateUserRequest user);
}
