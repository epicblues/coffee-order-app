package com.epicblues.coffee.server.order.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

  private final UUID orderId;
  private final LocalDateTime createdAt;
  private final List<OrderItem> orderItems;
  private final OrderStatus orderStatus;
  private Email email;
  private String address;
  private String postcode;
  private LocalDateTime updatedAt;

  public Order(UUID orderId, Email email, String address, String postcode,
      OrderStatus orderStatus, LocalDateTime createdAt,
      List<OrderItem> orderItems, LocalDateTime updatedAt) {
    this.orderId = orderId;
    this.email = email;
    this.address = address;
    this.postcode = postcode;
    this.orderStatus = orderStatus;
    this.createdAt = createdAt;
    this.orderItems = orderItems;
    this.updatedAt = updatedAt;
  }
}
