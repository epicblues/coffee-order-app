package com.epicblues.coffee.server.order.repositories;

import com.epicblues.coffee.server.order.entities.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

  Order insert(Order order);

  void removeAll();

  Optional<Order> findById(UUID orderId);

  List<Order> findAll();
}
