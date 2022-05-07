package com.epicblues.coffee.server.order.controllers;

import com.epicblues.coffee.server.order.OrderFixture;
import com.epicblues.coffee.server.order.entities.Order;
import com.epicblues.coffee.server.order.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {RestOrderController.class})
class RestOrderControllerTest {

  private static ObjectMapper objectMapper;
  private final ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private OrderService orderService;

  // 주문을 만드는 기능
  // request에 orderItem들에 관한 정보와,
  // order에 대한 개괄적인 정보들이 넘어온다.
  // 컨트롤러의 역할 -> DTO를 정확하게 받은 뒤에 서비스에 정확하게 호출한다.
  // vo에서 직접 도메인을 방문할 필요가 있을까?

  @Test
  @DisplayName("order를 post하는 요청이 들어오면 dto에 맞는 order를 생성하고 service에 호출해야 한다.")
  void createOrderTest() {
    var orderToTest = OrderFixture.singleOrder;
//    verify(orderService, calls(1)).create(orderArgumentCaptor.capture());

    var orderItemDtos = orderToTest.getOrderItems().stream().map(orderItem ->
        new OrderItemRequestDto(orderItem.getProductId(), orderItem.getCategory(),
            orderItem.getPrice(),
            orderItem.getQuantity())
    ).collect(Collectors.toList());
    OrderRequestDto orderRequestDto = new OrderRequestDto(orderToTest.getEmail().toString(),
        orderToTest.getAddress(),
        orderToTest.getPostcode(), orderItemDtos);

    var order = orderArgumentCaptor.getValue();
//    assertThat(order).isEqualTo(orderDto.convert());

  }

}
