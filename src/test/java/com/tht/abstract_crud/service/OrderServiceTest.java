package com.tht.abstract_crud.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tht.abstract_crud.model.base.BaseListRequest;
import com.tht.abstract_crud.model.order.request.CreateOrderRequest;
import com.tht.abstract_crud.model.user.User;

@SpringBootTest
public class OrderServiceTest {
  @Autowired
  private UserService userService;

  @Autowired
  private OrderService orderService;

  @Test
  void testCreateOrderList() {
    final int ORDER_COUNT = 15;
    final int USER_COUNT = 5;

    @SuppressWarnings("unchecked")
    List<User> users = (List<User>) userService.find(new BaseListRequest<>());

    if (users.size() < USER_COUNT)
      return;

    // Create orders for each user
    for (int i = 0; i < USER_COUNT; i++) {
      User user = users.get(i);

      for (int j = 0; j < ORDER_COUNT; j++) {
        orderService.createOrder(
            new CreateOrderRequest("Order for user " + user.getUsername(), new BigDecimal(100),
                "Description", user.getId()));
      }
    }
  }
}
