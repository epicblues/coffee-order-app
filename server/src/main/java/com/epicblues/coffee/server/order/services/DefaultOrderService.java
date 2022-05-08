package com.epicblues.coffee.server.order.services;

import com.epicblues.coffee.server.exceptions.ServiceException;
import com.epicblues.coffee.server.order.entities.Order;
import com.epicblues.coffee.server.order.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultOrderService implements OrderService {

  private final OrderRepository orderRepository;

  public DefaultOrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public Order create(Order order) {
    try {
      return orderRepository.insert(order);

    } catch (DataAccessException exception) {
      log.error("Repository Exception", exception);
      throw new ServiceException(exception);
    }
  }
}
