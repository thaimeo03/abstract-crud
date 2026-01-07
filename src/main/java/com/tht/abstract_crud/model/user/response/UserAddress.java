package com.tht.abstract_crud.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAddress {
  private Long id;
  private String fullName;
  private String address;
}
