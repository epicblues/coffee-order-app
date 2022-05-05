package com.epicblues.coffee.server.product.controllers;

import com.epicblues.coffee.server.product.entities.Product;
import com.epicblues.coffee.server.product.repositories.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

  private final ProductRepository repository;

  public ProductController(ProductRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/admin/products")
  public String showAllProducts(Model model) {
    var products = repository.findAll();
    model.addAttribute("products", products);
    return "product-list";
  }

  @GetMapping("/admin/product-registration")
  public String showProductRegistrationPage(Model model) {
    return "new-product";
  }

  @PostMapping("/admin/product-registration")
  public String createProductAndRedirectToMainPage(
      @ModelAttribute ProductRegistrationDto dto) {
    repository.save(dto.toProduct());
    return "redirect:/admin/products";
  }

  @ResponseBody
  @GetMapping("/api/v1/products")
  public List<Product> getAllProducts() {
    return repository.findAll();
  }

}
