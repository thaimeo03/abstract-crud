package com.tht.abstract_crud.service;

import com.tht.abstract_crud.model.order.request.CreateOrderRequest;

public interface OrderService {
  void createOrder(CreateOrderRequest request) throws RuntimeException;
}
