package com.tht.abstract_crud.model.user.response;

import java.util.List;

import com.tht.abstract_crud.model.order.response.OrderInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserOrders {
  private Long id;
  private String fullName;
  private String address;
  private List<OrderInfo> orderInfos;
}
