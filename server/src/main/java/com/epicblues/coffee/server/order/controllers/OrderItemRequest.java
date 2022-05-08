package com.epicblues.coffee.server.order.controllers;

import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.order.entities.OrderItem;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderItemRequest {

  private final UUID productId;
  private final Category category;
  private final Long price;
  private final Long quantity;

  public OrderItem convert(UUID orderId) {
    return new OrderItem(orderId, productId, category, price, quantity, LocalDateTime.now(), null);
  }
}
