package com.tht.abstract_crud.model.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseListRequest<F> {
  private String isPaging = "N"; // Y, N
  private Integer page = 0;
  private Integer size = 10;
  private String sort;
  private F filter;
}
