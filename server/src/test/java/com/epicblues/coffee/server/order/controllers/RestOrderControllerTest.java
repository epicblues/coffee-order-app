package com.epicblues.coffee.server.order.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epicblues.coffee.server.order.entities.Order;
import com.epicblues.coffee.server.order.services.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {RestOrderController.class})
class RestOrderControllerTest {

  private final ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
  private final String orderJson = "{\n"
      + "  \"email\": \"epicblue@hanmail.net\",\n"
      + "  \"address\": \"답십리동\",\n"
      + "  \"postcode\": \"12345\",\n"
      + "  \"orderItems\": [\n"
      + "    {\n"
      + "      \"productId\": \"8e9cdd65-0824-4283-80cf-4c8e2f03a800\",\n"
      + "      \"category\": \"DRINK\",\n"
      + "      \"price\": 10000,\n"
      + "      \"quantity\": 3\n"
      + "    }\n"
      + "  ]\n"
      + "}\n";
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
  void createOrderTest() throws Exception {

    mockMvc.perform(
            post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(orderJson))
        .andDo(print()).andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").exists());

    verify(orderService, times(1)).create(orderArgumentCaptor.capture());
    var order = orderArgumentCaptor.getValue();

    assertThat(order.getEmail().getAddress()).isEqualTo("epicblue@hanmail.net");
    assertThat(order.getOrderItems().get(0).getProductId())
        .hasToString("8e9cdd65-0824-4283-80cf-4c8e2f03a800");
    assertThat(order.getPostcode()).isEqualTo("12345");
  }

}
