package com.epicblues.coffee.server.order.services;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epicblues.coffee.server.exceptions.ServiceException;
import com.epicblues.coffee.server.order.OrderFixture;
import com.epicblues.coffee.server.order.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

@ExtendWith(MockitoExtension.class)
class DefaultOrderServiceTest {

  private OrderService orderService;
  @Mock
  private OrderRepository orderRepository;

  @BeforeEach
  void setup() {
    this.orderService = new DefaultOrderService(orderRepository);
  }

  @Test
  void create_method_should_call_repository_insert() {

    var order = OrderFixture.singleOrder;

    orderService.create(order);

    verify(orderRepository, times(1)).insert(order);
  }

  @Test
  void translate_repository_exception_to_service_exception() {

    var order = OrderFixture.singleOrder;
    when(orderRepository.insert(order)).thenThrow(new DuplicateKeyException("Primary Key"));

    assertThatCode(() -> {
      orderService.create(order);
    }).isInstanceOf(ServiceException.class);

    verify(orderRepository, times(1)).insert(order);
  }
}
