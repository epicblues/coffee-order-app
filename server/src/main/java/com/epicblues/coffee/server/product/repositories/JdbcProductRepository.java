package com.epicblues.coffee.server.product.repositories;

import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.product.entities.Product;
import com.epicblues.coffee.server.util.UUIDMapper;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JdbcProductRepository implements ProductRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RowMapper<Product> productRowMapper = (resultSet, ignored) -> {
    UUID productId = UUIDMapper.fromBytes(resultSet.getBytes("product_id"));
    String productName = resultSet.getString("product_name");
    Category category = Category.valueOf(resultSet.getString("category"));
    var price = resultSet.getLong("price");
    String description = resultSet.getString("description");
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    var updatedAt = resultSet.getTimestamp("updated_at") == null ? null
        : resultSet.getTimestamp("updated_at").toLocalDateTime();
    return new Product(productId, productName, category, price, description, createdAt, updatedAt);
  };

  public JdbcProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Product> findById(UUID productId) {
    try {
      var product = jdbcTemplate.queryForObject(
          "SELECT * FROM products WHERE product_id = UUID_TO_BIN(:productId)",
          Map.of("productId", UUIDMapper.toBytes(productId)), productRowMapper);
      return Optional.ofNullable(product);
    } catch (DataAccessException exception) {
      log.error("DataAccessException", exception);
      return Optional.empty();
    }
  }

  @Override
  public List<Product> findAll() {
    return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
  }

  @Override
  public Product save(Product product) {
    jdbcTemplate.update(
        "INSERT INTO products(product_id, product_name, category, price, description, created_at, updated_at) VALUE "
            + "(UUID_TO_BIN(:productId),:productName, :category, :price, :description, :createdAt, :updatedAt)",
        toParamMap(product));

    return product;
  }

  @Override
  public void removeAll() {
    jdbcTemplate.update("DELETE FROM products", Collections.emptyMap());
  }

  private Map<String, ?> toParamMap(Product product) {

    var map = new HashMap<String, Object>();
    map.put("productId", product.getProductId().toString().getBytes(StandardCharsets.UTF_8));
    map.put("productName", product.getProductName());
    map.put("category", product.getCategory().toString());
    map.put("price", product.getPrice());
    map.put("description", product.getDescription());
    map.put("createdAt", product.getCreatedAt());
    map.put("updatedAt", product.getUpdatedAt());
    return map;
  }
}
