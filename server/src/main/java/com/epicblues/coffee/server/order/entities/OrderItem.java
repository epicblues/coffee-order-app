package com.epicblues.coffee.server.order.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItem {

  private final UUID orderId;
  private final UUID productId;
  private final Category category;
  private final Long price;
  private final Long quantity;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public OrderItem(UUID orderId, UUID productId, Category category, Long price,
      Long quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.orderId = orderId;
    this.productId = productId;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
