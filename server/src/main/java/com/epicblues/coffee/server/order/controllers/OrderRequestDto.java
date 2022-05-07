package com.epicblues.coffee.server.order.controllers;

import com.epicblues.coffee.server.order.entities.Email;
import com.epicblues.coffee.server.order.entities.Order;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class OrderRequestDto {

  private final String email;
  private final String address;
  private final String postcode;
  private final List<OrderItemRequestDto> orderItemRequestDtoList;

  public Order convert() {
    var newOrderId = UUID.randomUUID();
    return new Order(newOrderId, new Email(email), address, postcode,
        orderItemRequestDtoList.stream().map(orderItemDto -> orderItemDto.convert(newOrderId))
            .collect(
                Collectors.toList()));
  }
}
