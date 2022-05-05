package com.epicblues.coffee.server.product.repositories;

import com.epicblues.coffee.server.product.entities.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

  Optional<Product> findById(UUID productId);

  List<Product> findAll();

  Product save(Product product);

  void removeAll();
}
