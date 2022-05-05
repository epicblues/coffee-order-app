package com.epicblues.coffee.server.product.controllers;

import com.epicblues.coffee.server.order.entities.Category;
import com.epicblues.coffee.server.product.entities.Product;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ProductRegistrationDto {

  @Setter
  private String productName;
  @Setter
  private Category category;
  @Setter
  private long price;
  @Setter
  private String description;

  public Product toProduct() {
    var product = new Product(UUID.randomUUID(), productName, category, price, LocalDateTime.now());
    product.setDescription(description);
    return product;
  }

}
