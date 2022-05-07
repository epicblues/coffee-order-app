package com.epicblues.coffee.server;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.order.OrderFixture;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

class ObjectMapperTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Test
  @DisplayName("제대로 serialize 하는가")
  void objectSerializeTest() throws IOException {
    var orderItemList = OrderFixture.orderItemsLists.get(1);
    String serializedOrderItems = mapper.writeValueAsString(orderItemList);

    var order = OrderFixture.singleOrder;

    assertThat(mapper.writeValueAsString(order)).contains(serializedOrderItems,
        order.getOrderId().toString());
  }

}


