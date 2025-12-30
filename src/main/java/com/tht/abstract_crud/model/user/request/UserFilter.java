
package com.tht.abstract_crud.model.user.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserFilter {
  private String keyword;
  private Integer status;
  private LocalDate fromDate;
  private LocalDate toDate;
}
