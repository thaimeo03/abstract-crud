package com.tht.abstract_crud.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.tht.abstract_crud.enums.FilterOperator;
import com.tht.abstract_crud.model.base.FilterCondition;
import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.model.user.request.CreateUserRequest;
import com.tht.abstract_crud.model.user.request.UserFilter;
import com.tht.abstract_crud.repository.UserRepository;
import com.tht.abstract_crud.service.UserService;
import com.tht.abstract_crud.service.base.BaseListService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends BaseListService<User, UserFilter> implements UserService {
  private final UserRepository userRepository;

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User save(CreateUserRequest user) {
    User newUser = User.builder()
        .username(user.getUsername())
        .fullName(user.getFullName())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .address(user.getAddress())
        .build();

    return userRepository.save(newUser);
  }

  @Override
  protected List<FilterCondition> buildFilterConditions(UserFilter filter) {
    List<FilterCondition> conditions = new ArrayList<>();

    if (filter == null)
      return conditions;

    if (filter.getKeyword().length() > 0) {
      conditions.add(new FilterCondition("fullName", FilterOperator.LIKE, filter.getKeyword(), null));
    }

    if (filter.getFromDate() != null) {
      conditions.add(new FilterCondition("createdAt", FilterOperator.BETWEEN, filter.getFromDate().atStartOfDay(),
          filter.getToDate().atTime(23, 59, 59)));
    }

    return conditions;
  }

  @Override
  protected JpaSpecificationExecutor<User> getRepository() {
    return userRepository;
  }
}
