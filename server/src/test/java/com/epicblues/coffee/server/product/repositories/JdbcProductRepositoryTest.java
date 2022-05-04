package com.epicblues.coffee.server.product.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.EmbeddedDatabaseTestModule;
import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.product.entities.Product;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

class JdbcProductRepositoryTest {

  private static final List<Product> productFixtures = List.of(
      new Product(UUID.randomUUID(), "Americano", Category.DRINK, 10000L, LocalDateTime.now()),
      new Product(UUID.randomUUID(), "Vanilla Latte", Category.DRINK, 5000L, LocalDateTime.now())
  );

  //  jdbcTemplate.update(
//      String.format(
//      "INSERT INTO products(product_id, product_name, category, price, created_at)\n"
//      + "    VALUE (UUID_TO_BIN('%s'), 'Americano',\n"
//      + "           'DRINK', 10000, now());", productIds.get(0).toString()));
//      jdbcTemplate.update(
//          String.format(
//          "INSERT INTO products(product_id, product_name, category, price, created_at)\n"
//          + "    VALUE (UUID_TO_BIN('%s'), 'Vanilla Latte',\n"
//          + "           'DRINK', 5000, now());", productIds.get(1).toString()));
  private static JdbcProductRepository repository;

  @BeforeAll
  static void setup() {
    repository = new JdbcProductRepository(
        new NamedParameterJdbcTemplate(EmbeddedDatabaseTestModule.getDataSource()));
  }

  @BeforeEach
  void insertFixture() {
    repository.save(productFixtures.get(0));
  }

  @Test
  void findById() {
    var uuid = productFixtures.get(0).getProductId();

    var product = repository.findById(uuid);

    assertThat(product).isNotEmpty();
    assertThat(product.get().getProductId()).isEqualTo(uuid);
  }

  @Test
  void save() {
    var secondProduct = productFixtures.get(1);

    repository.save(secondProduct);
    var queriedSecondProduct = repository.findById(secondProduct.getProductId());

    assertThat(queriedSecondProduct).isNotEmpty().get().isEqualTo(secondProduct);
  }

  @Test
  void findAll_should_query_all_products() {
    var products = repository.findAll();
    assertThat(products).hasSize(1).contains(productFixtures.get(0));
  }

}
