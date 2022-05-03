package com.epicblues.coffee.server.order;

import com.epicblues.coffee.server.order.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }
}
