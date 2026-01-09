package com.tht.abstract_crud.model.order.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInfo {
  private Long id;
  private String name;
  private BigDecimal price;
  private String description;
}
