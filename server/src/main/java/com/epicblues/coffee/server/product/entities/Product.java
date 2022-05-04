package com.epicblues.coffee.server.product.entities;

import com.epicblues.coffee.server.order.entities.Category;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

  private final UUID productId;
  private final String productName;
  private final Category category;
  private final Long price;
  private final LocalDateTime createdAt;
  private String description = null;
  private LocalDateTime updatedAt = null;

  public Product(UUID productId, String productName, Category category, Long price,
      String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.productId = productId;
    this.productName = productName;
    this.category = category;
    this.price = price;
    this.createdAt = createdAt;
    this.description = description;
    this.updatedAt = updatedAt;
  }

  public Product(UUID productId, String productName, Category category, Long price,
      LocalDateTime createdAt) {
    this.productId = productId;
    this.productName = productName;
    this.category = category;
    this.price = price;
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Product)) {
      return false;
    }
    Product product = (Product) o;
    return productId.equals(product.productId) && productName.equals(product.productName)
        && category == product.category && price.equals(product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, productName, category, price);
  }
}
