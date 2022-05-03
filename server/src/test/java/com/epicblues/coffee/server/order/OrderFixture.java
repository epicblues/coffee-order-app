package com.epicblues.coffee.server.order;

import com.epicblues.coffee.server.EmbeddedDatabaseTestModule;
import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.order.entities.Email;
import com.epicblues.coffee.server.order.entities.Order;
import com.epicblues.coffee.server.order.entities.OrderItem;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;

public class OrderFixture {

  private static final List<UUID> orderIds = List.of(UUID.randomUUID(), UUID.randomUUID(),
      UUID.randomUUID());
  private static final List<UUID> productIds = List.of(
      UUID.fromString("067badbc-e6cb-4acd-93b6-b74275f8cb8b"),
      UUID.fromString("c75aae26-c527-4590-b70d-53cd28916976"),
      UUID.fromString("c75aae26-c527-4590-b70d-53cd2891697"));
  private static final List<LocalDateTime> createdAts =
      List.of(LocalDateTime.now(), LocalDateTime.now().minus(10, ChronoUnit.DAYS),
          LocalDateTime.now());
  public static final List<List<OrderItem>> orderItemsLists = List.of(
      List.of(
          new OrderItem(orderIds.get(0), productIds.get(0), Category.COFFEE_BEAN_PACKAGE, 10000L,
              5L,
              createdAts.get(0), createdAts.get(0))),
      List.of(new OrderItem(orderIds.get(1), productIds.get(1), Category.DRINK, 5000L, 3L,
          createdAts.get(1), createdAts.get(1)))
  );
  public static final List<Order> orders = List.of(
      new Order(orderIds.get(0), new Email("epicblue@hanmail.net"), "답십리동", "23567",
          orderItemsLists.get(0)),

      new Order(orderIds.get(1), new Email("minsung@hanmail.net"), "대치동", "4447",
          orderItemsLists.get(1))

  );

  public static final Order singleOrder =
      new Order(orderIds.get(2), new Email("poser@hanmail.net"), "연어", "1023",
          orderItemsLists.get(1));

  public static class ProductSetupExecutor {

    private final static JdbcTemplate jdbcTemplate = new JdbcTemplate(
        EmbeddedDatabaseTestModule.getDataSource());

    public static void insertProducts() {

      jdbcTemplate.update(
          String.format(
              "INSERT INTO products(product_id, product_name, category, price, created_at)\n"
                  + "    VALUE (UUID_TO_BIN('%s'), 'Americano',\n"
                  + "           'DRINK', 10000, now());", productIds.get(0).toString()));
      jdbcTemplate.update(
          String.format(
              "INSERT INTO products(product_id, product_name, category, price, created_at)\n"
                  + "    VALUE (UUID_TO_BIN('%s'), 'Vanilla Latte',\n"
                  + "           'DRINK', 5000, now());", productIds.get(1).toString()));

    }

    public static void removeAllProducts() {
      jdbcTemplate.update("DELETE FROM products");
    }

  }
}
