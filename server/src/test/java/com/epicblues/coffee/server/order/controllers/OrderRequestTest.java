package com.epicblues.coffee.server.order.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.order.OrderFixture;
import com.epicblues.coffee.server.order.entities.Category;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderRequestTest {

  @Test
  void can_convert_to_order_entity() {
    var order = OrderFixture.singleOrder;
    var productId = UUID.randomUUID();

    var orderDto = new OrderRequest(order.getEmail().getAddress(), order.getAddress(),
        order.getPostcode(),
        List.of(new OrderItemRequest(productId, Category.DRINK, 5000L, 3L))
    );
    var convertedOrder = orderDto.convert();

    assertThat(convertedOrder.getOrderItems()).allMatch(
        orderItem -> orderItem.getProductId().equals(productId));
    assertThat(convertedOrder.getPostcode()).isEqualTo(orderDto.getPostcode());
    assertThat(convertedOrder.getEmail()).hasToString(orderDto.getEmail());
    assertThat(convertedOrder.getAddress()).isEqualTo(orderDto.getAddress());
  }
}
