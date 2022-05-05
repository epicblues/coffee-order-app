package com.epicblues.coffee.server.order.controllers;

import com.epicblues.coffee.server.order.services.OrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestOrderController {

  private final OrderService orderService;

  public RestOrderController(OrderService orderService) {
    this.orderService = orderService;
  }
}
