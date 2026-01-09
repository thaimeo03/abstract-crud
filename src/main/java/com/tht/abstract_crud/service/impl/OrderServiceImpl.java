package com.tht.abstract_crud.service.impl;

import org.springframework.stereotype.Service;

import com.tht.abstract_crud.model.order.Order;
import com.tht.abstract_crud.model.order.request.CreateOrderRequest;
import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.repository.OrderRepository;
import com.tht.abstract_crud.service.OrderService;
import com.tht.abstract_crud.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final UserService userService;

  @Override
  public void createOrder(CreateOrderRequest request) throws RuntimeException {
    // Find and check user exists
    User user = userService.findById(request.getUserId());

    // Save order
    Order newOrder = Order.builder()
        .name(request.getName())
        .price(request.getPrice())
        .description(request.getDescription())
        .user(user)
        .build();

    orderRepository.save(newOrder);
  }
}
