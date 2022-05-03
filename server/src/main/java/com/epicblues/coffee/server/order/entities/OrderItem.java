package com.epicblues.coffee.server.order.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderItem {

  private final UUID orderId;
  private final UUID productId;
  private final Category category;
  private final Long price;
  private final Long quantity;
  private final LocalDateTime createdAt;
  @Setter
  private LocalDateTime updatedAt;

  public OrderItem(UUID orderId, UUID productId, Category category, Long price, Long quantity,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.orderId = orderId;
    this.productId = productId;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrderItem)) {
      return false;
    }
    OrderItem orderItem = (OrderItem) o;
    return orderId.equals(orderItem.orderId) && productId.equals(orderItem.productId)
        && category == orderItem.category && price.equals(orderItem.price) && quantity.equals(
        orderItem.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, productId, category, price, quantity);
  }
}
