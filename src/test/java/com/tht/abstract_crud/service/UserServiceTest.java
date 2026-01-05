package com.tht.abstract_crud.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tht.abstract_crud.model.user.request.CreateUserRequest;

@SpringBootTest
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @Test
  void testCreateManyUsers() {
    for (int i = 1; i <= 50; i++) {
      CreateUserRequest request = CreateUserRequest.builder()
          .username("user" + i)
          .fullName("User " + i)
          .email("user" + i + "@example.com")
          .phoneNumber("123-456-789" + i)
          .address("Address " + i)
          .build();

      userService.save(request);
    }
  }
}
