package com.epicblues.coffee.server.product.controllers;

import com.epicblues.coffee.server.product.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
  public String createProductAndRedirectToMainPage(@ModelAttribute ProductRegistrationDto dto) {
    repository.save(dto.toProduct());
    return "redirect:/admin/products";
  }
}
