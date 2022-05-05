package com.epicblues.coffee.server.product.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epicblues.coffee.server.product.ProductFixtureList;
import com.epicblues.coffee.server.product.entities.Product;
import com.epicblues.coffee.server.product.repositories.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class ProductControllerTest {

  @MockBean
  ProductRepository productRepository;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("전체 product에 대한 page view를 리턴해야 한다.")
  void specific_request_show_product_page_view() throws Exception {
    List<Product> products = ProductFixtureList.getAll();
    Mockito.when(productRepository.findAll()).thenReturn(products);

    mockMvc.perform(get("/admin/products"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("text/html;charset=UTF-8"))
        .andExpect(content().string(containsString(products.get(0).getProductName())))
        .andExpect(content().string(containsString(products.get(1).getProductName())));

  }
}
