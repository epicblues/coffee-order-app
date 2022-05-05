package com.epicblues.coffee.server.order.services;

import com.epicblues.coffee.server.order.entities.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

  Order create(Order order);
}
