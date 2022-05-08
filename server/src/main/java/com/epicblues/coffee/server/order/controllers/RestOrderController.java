package com.epicblues.coffee.server.order.controllers;

import com.epicblues.coffee.server.order.services.OrderService;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestOrderController {

  private final OrderService orderService;

  public RestOrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/api/v1/orders")
  public Map<String, Object> createOrder(@RequestBody OrderRequest orderDto) {
    var order = orderDto.convert();

    orderService.create(order);

    return Map.of("id", order.getOrderId());
  }
}
