package com.tht.abstract_crud.model.order.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderRequest {
  private String name;

  private BigDecimal price;

  private String description;

  private Long userId;
}
