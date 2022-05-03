package com.epicblues.coffee.server.order.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.EmbeddedDatabaseTestModule;
import com.epicblues.coffee.server.order.OrderFixture;
import com.epicblues.coffee.server.order.OrderFixture.ProductSetupExecutor;
import com.epicblues.coffee.server.order.entities.Order;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

class JdbcOrderRepositoryTest {

  private static JdbcOrderRepository repository;

  @BeforeAll
  static void setup() {
    repository = new JdbcOrderRepository(
        new NamedParameterJdbcTemplate(EmbeddedDatabaseTestModule.getDataSource()));
  }

  @BeforeEach
  @Transactional
  void insertFixtures() {
    ProductSetupExecutor.insertProducts();
    OrderFixture.orders.forEach(repository::insert);
  }

  @AfterEach()
  @Transactional
  void cleanUpOrderTable() {
    repository.removeAll();
    ProductSetupExecutor.removeAllProducts();
  }

  @Test
  void findAll() {
    List<Order> orders = repository.findAll();
    assertThat(orders).hasSize(OrderFixture.orders.size())
        .containsAll(OrderFixture.orders);
  }

  @Test
  void insert() {
    var order = OrderFixture.singleOrder;
    Order queriedOrder = repository.insert(order);
    assertThat(repository.findById(queriedOrder.getOrderId())).get().isEqualTo(queriedOrder);
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
    repository.removeAll();
    assertThat(repository.findAll()).isEmpty();
  }
}
