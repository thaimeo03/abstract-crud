package com.tht.abstract_crud.model.base;

import lombok.Data;

@Data
public class BaseListRequest<F> {
  private String isPaging; // Y, N
  private Integer page;
  private Integer size;
  private String sort;
  private F filter;
}
