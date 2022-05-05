package com.epicblues.coffee.server.product.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.epicblues.coffee.server.EmbeddedDatabaseTestModule;
import com.epicblues.coffee.server.product.ProductFixtureList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

class JdbcProductRepositoryTest {

  private static JdbcProductRepository repository;

  @BeforeAll
  static void setup() {
    repository = new JdbcProductRepository(
        new NamedParameterJdbcTemplate(EmbeddedDatabaseTestModule.getDataSource()));
  }

  @BeforeEach
  void insertFixture() {
    repository.save(ProductFixtureList.get(0));
  }

  @AfterEach
  void cleanUp() {
    repository.removeAll();
  }

  @Test
  void findById() {
    var uuid = ProductFixtureList.get(0).getProductId();

    var product = repository.findById(uuid);

    assertThat(product).isNotEmpty();
    assertThat(product.get().getProductId()).isEqualTo(uuid);
  }

  @Test
  void save() {
    var secondProduct = ProductFixtureList.get(1);

    repository.save(secondProduct);
    var queriedSecondProduct = repository.findById(secondProduct.getProductId());

    assertThat(queriedSecondProduct).isNotEmpty().get().isEqualTo(secondProduct);
  }

  @Test
  void findAll_should_query_all_products() {
    var products = repository.findAll();
    assertThat(products).hasSize(1).contains(ProductFixtureList.get(0));
  }

}
