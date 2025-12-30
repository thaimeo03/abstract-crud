package com.tht.abstract_crud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tht.abstract_crud.model.base.BaseListRequest;
import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.model.user.request.CreateUserRequest;
import com.tht.abstract_crud.model.user.request.UserFilter;
import com.tht.abstract_crud.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
  private final UserService userService;

  @PostMapping("")
  public ResponseEntity<User> createUser(@RequestBody CreateUserRequest userRequest) {
    User user = userService.save(userRequest);

    return ResponseEntity.status(HttpStatus.OK)
        .body(user);
  }

  @GetMapping("/list")
  public ResponseEntity<Object> getMethodName(@ModelAttribute BaseListRequest<UserFilter> request) {
    Object response = userService.find(request);

    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

}
