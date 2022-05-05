package com.epicblues.coffee.server.order.controllers;

import com.epicblues.coffee.server.order.OrderFixture;
import com.epicblues.coffee.server.order.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {RestOrderController.class})
class RestOrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  // 주문을 만드는 기능
  // request에 orderItem들에 관한 정보와,
  // order에 대한 개괄적인 정보들이 넘어온다.
  // vo에서 직접 도메인을 방문할 필요가 있을까?

  @Test
  void createOrderTest() {
    var orderToTest = OrderFixture.singleOrder;
  }
}
