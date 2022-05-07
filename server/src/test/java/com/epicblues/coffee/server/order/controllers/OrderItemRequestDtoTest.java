package com.epicblues.coffee.server.order.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.order.entities.OrderItem;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderItemRequestDtoTest {

  @Test
  void can_convert_to_order_item() {
    var orderId = UUID.randomUUID();
    OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto(UUID.randomUUID(),
        Category.DRINK, 10000L, 3L);
    OrderItem convertedOrderItem = orderItemRequestDto.convert(orderId);

    assertThat(orderItemRequestDto.getProductId()).isEqualTo(convertedOrderItem.getProductId());
    assertThat(orderItemRequestDto.getPrice()).isEqualTo(convertedOrderItem.getPrice());
    assertThat(orderItemRequestDto.getCategory()).isEqualTo(convertedOrderItem.getCategory());
  }

}
