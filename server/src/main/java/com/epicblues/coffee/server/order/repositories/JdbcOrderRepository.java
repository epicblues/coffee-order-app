package com.epicblues.coffee.server.order.repositories;

import static com.epicblues.coffee.server.order.entities.Order.Key.ADDRESS;
import static com.epicblues.coffee.server.order.entities.Order.Key.CREATED_AT;
import static com.epicblues.coffee.server.order.entities.Order.Key.EMAIL;
import static com.epicblues.coffee.server.order.entities.Order.Key.ORDER_ID;
import static com.epicblues.coffee.server.order.entities.Order.Key.ORDER_STATUS;
import static com.epicblues.coffee.server.order.entities.Order.Key.POSTCODE;
import static com.epicblues.coffee.server.order.entities.Order.Key.UPDATED_AT;

import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.order.entities.Email;
import com.epicblues.coffee.server.order.entities.Order;
import com.epicblues.coffee.server.order.entities.OrderItem;
import com.epicblues.coffee.server.order.entities.OrderStatus;
import com.epicblues.coffee.server.util.UUIDMapper;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class JdbcOrderRepository implements OrderRepository {

  private static final String UPDATED_AT_COLUMN = "updated_at";
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RowMapper<Order> orderRowMapper = (resultSet, index) ->
      Order.Builder.create()
          .orderId(UUIDMapper.fromBytes(resultSet.getBytes("order_id")))
          .email(new Email(resultSet.getString("email")))
          .address(resultSet.getString("address"))
          .postcode(resultSet.getString("postcode"))
          .orderStatus(OrderStatus.valueOf(resultSet.getString("order_status")))
          .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
          .updatedAt(resultSet.getTimestamp(UPDATED_AT_COLUMN) == null ? null
              : resultSet.getTimestamp(UPDATED_AT_COLUMN).toLocalDateTime())
          .build();
  private final RowMapper<OrderItem> orderItemRowMapper = (resultSet, index) -> {
    UUID orderId = UUIDMapper.fromBytes(resultSet.getBytes("order_id"));
    UUID productId = UUIDMapper.fromBytes(resultSet.getBytes("product_id"));
    Category category = Category.valueOf(resultSet.getString("category"));
    var price = resultSet.getLong("price");
    var quantity = resultSet.getLong("quantity");
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    var updatedAt = resultSet.getTimestamp(UPDATED_AT_COLUMN) == null ? null
        : resultSet.getTimestamp(UPDATED_AT_COLUMN).toLocalDateTime();

    return new OrderItem(orderId, productId, category, price, quantity, createdAt, updatedAt);

  };

  public JdbcOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional
  @Override
  public Order insert(Order order) {
    // Order를 먼저 삽입한다.
    jdbcTemplate.update(
        String.format(
            "INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at) "
                + "VALUE (UUID_TO_BIN(:%s), :%s, :%s, :%s, :%s, :%s, :%s)",
            ORDER_ID, EMAIL, ADDRESS, POSTCODE, ORDER_STATUS, CREATED_AT, UPDATED_AT),
        mapToParam(order));
    order.getOrderItems().forEach(orderItem ->
        jdbcTemplate.update(
            "INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) "
                + "VALUE (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
            mapToParam(orderItem)
        )
    );
    return order;
  }

  private Map<String, ?> mapToParam(Order order) {
    var map = new HashMap<String, Object>();
    map.put(ORDER_ID,
        order.getOrderId().toString().getBytes(StandardCharsets.UTF_8));
    map.put(EMAIL, order.getEmail().getAddress());
    map.put(ADDRESS, order.getAddress());
    map.put(POSTCODE, order.getPostcode());
    map.put(ORDER_STATUS, order.getOrderStatus().toString());
    map.put(CREATED_AT, order.getCreatedAt());
    map.put(UPDATED_AT, order.getUpdatedAt());
    return map;
  }

  private Map<String, ?> mapToParam(OrderItem orderItem) {
    var map = new HashMap<String, Object>();
    map.put("orderId",
        orderItem.getOrderId().toString().getBytes(StandardCharsets.UTF_8));
    map.put("productId",
        orderItem.getProductId().toString().getBytes(StandardCharsets.UTF_8));
    map.put("category", orderItem.getCategory().toString());
    map.put("price", orderItem.getPrice());
    map.put("quantity", orderItem.getQuantity());
    map.put("createdAt", orderItem.getCreatedAt());
    map.put("updatedAt", orderItem.getUpdatedAt());
    return map;
  }

  @Override
  public void removeAll() {
    jdbcTemplate.update("DELETE FROM orders", Collections.emptyMap());
  }

  @Override
  public Optional<Order> findById(UUID orderId) {
    try {
      var order = jdbcTemplate.queryForObject(
          "SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
          Map.of(ORDER_ID, UUIDMapper.toBytes(orderId)), orderRowMapper);

      var orderItems = jdbcTemplate.query(
          "SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)",
          Map.of("orderId", UUIDMapper.toBytes(orderId)), orderItemRowMapper);
      Objects.requireNonNull(order).setOrderItems(orderItems);
      return Optional.of(order);

    } catch (DataAccessException exception) {
      log.error(exception.getMessage(), exception);
      return Optional.empty();
    }
  }

  @Override
  public List<Order> findAll() {
    return jdbcTemplate.query("SELECT * FROM orders", Collections.emptyMap(),
        orderRowMapper);
  }
}
