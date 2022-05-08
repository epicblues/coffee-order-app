package com.epicblues.coffee.server.order.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.order.entities.OrderItem;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderItemRequestTest {

  @Test
  void can_convert_to_order_item() {
    var orderId = UUID.randomUUID();
    OrderItemRequest orderItemRequest = new OrderItemRequest(UUID.randomUUID(),
        Category.DRINK, 10000L, 3L);
    OrderItem convertedOrderItem = orderItemRequest.convert(orderId);

    assertThat(orderItemRequest.getProductId()).isEqualTo(convertedOrderItem.getProductId());
    assertThat(orderItemRequest.getPrice()).isEqualTo(convertedOrderItem.getPrice());
    assertThat(orderItemRequest.getCategory()).isEqualTo(convertedOrderItem.getCategory());
  }

}
