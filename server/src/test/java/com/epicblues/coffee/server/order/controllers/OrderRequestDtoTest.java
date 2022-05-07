package com.epicblues.coffee.server.order.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.order.OrderFixture;
import com.epicblues.coffee.server.order.entities.Category;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderRequestDtoTest {

  @Test
  void can_convert_to_order_entity() {
    var order = OrderFixture.singleOrder;
    var productId = UUID.randomUUID();

    var orderDto = new OrderRequestDto(order.getEmail().getAddress(), order.getAddress(),
        order.getPostcode(),
        List.of(new OrderItemRequestDto(productId, Category.DRINK, 5000L, 3L))
    );
    var convertedOrder = orderDto.convert();

    // 나는 order가 제대로 변환되었기를 바랄 뿐이다.
    assertThat(convertedOrder.getOrderItems()).allMatch(
        orderItem -> orderItem.getProductId().equals(productId));
    assertThat(convertedOrder.getPostcode()).isEqualTo(order.getPostcode());
    assertThat(convertedOrder.getEmail()).isEqualTo(order.getEmail());
  }
}
