package com.tht.abstract_crud.service;

import java.util.Optional;

import com.tht.abstract_crud.model.base.BaseListRequest;
import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.model.user.request.CreateUserRequest;
import com.tht.abstract_crud.model.user.request.UserFilter;

public interface UserService {
  Optional<User> findByUsername(String username);

  User save(CreateUserRequest user);

  Object find(BaseListRequest<UserFilter> request);

  User findById(Long id);
}
