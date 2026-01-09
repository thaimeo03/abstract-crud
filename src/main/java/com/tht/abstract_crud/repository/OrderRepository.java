package com.tht.abstract_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tht.abstract_crud.model.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
