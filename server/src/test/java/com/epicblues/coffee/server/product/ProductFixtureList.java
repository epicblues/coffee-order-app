package com.epicblues.coffee.server.product;

import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.product.entities.Product;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProductFixtureList {

  private static final List<Product> list = List.of(
      new Product(UUID.randomUUID(), "Americano", Category.DRINK, 10000L, LocalDateTime.now()),
      new Product(UUID.randomUUID(), "Vanilla Latte", Category.DRINK, 5000L, LocalDateTime.now())
  );

  private ProductFixtureList() {
  }

  public static Product get(int index) {
    return list.get(index);
  }

  public static List<Product> getAll() {
    return list;
  }
}
