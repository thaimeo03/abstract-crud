package com.tht.abstract_crud.model.user.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
  private String username;

  private String fullName;

  private String email;

  private String phoneNumber;

  private String address;
}
