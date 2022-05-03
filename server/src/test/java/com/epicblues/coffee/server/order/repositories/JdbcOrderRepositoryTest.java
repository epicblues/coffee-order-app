package com.epicblues.coffee.server.order.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.epicblues.coffee.server.EmbeddedDatabaseTestModule;
import com.epicblues.coffee.server.order.OrderFixture;
import com.epicblues.coffee.server.order.entities.Order;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

class JdbcOrderRepositoryTest extends EmbeddedDatabaseTestModule {

  private static JdbcOrderRepository repository;

  @BeforeAll
  static void setup() {
    repository = new JdbcOrderRepository(new NamedParameterJdbcTemplate(getDataSource()));
  }

  @BeforeEach
  void insertFixtures() {
    repository.insert(OrderFixture.orders.get(0));
  }

  @AfterEach()
  void cleanUpOrderTable() {
    repository.removeAll();
  }

  @Test
  void findAll() {

    List<Order> orders = repository.findAll();

    assertThat(orders).hasSize(1)
        .contains(OrderFixture.orders.get(0));
  }

  @Test
  void insert() {
    var order = OrderFixture.orders.get(1);
    assertThatNoException().isThrownBy(() -> {
      repository.insert(order);
    });

  }

  @Test
  void findById() {
    var order = OrderFixture.orders.get(0);

    var queriedOrder = repository.findById(order.getOrderId());

    assertThat(queriedOrder).isNotEmpty().get().isEqualTo(order);
    assertThat(order.getOrderItems()).containsAll(queriedOrder.get().getOrderItems());
  }

  @Test
  void removeAll() {
    assertThatNoException().isThrownBy(() -> {
      repository.removeAll();
    });
    assertThat(repository.findAll()).isEmpty();
  }
}
