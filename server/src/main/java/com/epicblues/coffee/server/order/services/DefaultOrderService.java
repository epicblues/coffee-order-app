package com.epicblues.coffee.server.order.services;

import com.epicblues.coffee.server.order.entities.Order;
import com.epicblues.coffee.server.order.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

  private final OrderRepository orderRepository;

  public DefaultOrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public Order create(Order order) {
    return null;
  }
}
