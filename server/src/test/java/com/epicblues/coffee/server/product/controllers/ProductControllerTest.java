package com.epicblues.coffee.server.product.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epicblues.coffee.server.product.ProductFixtureList;
import com.epicblues.coffee.server.product.entities.Product;
import com.epicblues.coffee.server.product.repositories.ProductRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

  private static final String registerUrl = "/admin/product-registration";
  @MockBean
  ProductRepository productRepository;
  @Captor
  ArgumentCaptor<Product> productArgumentCaptor;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("전체 product에 대한 page view를 리턴해야 한다.")
  void specific_request_show_product_page_view() throws Exception {
    List<Product> products = ProductFixtureList.getAll();
    when(productRepository.findAll()).thenReturn(products);

    mockMvc.perform(get("/admin/products"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("text/html;charset=UTF-8"))
        .andExpect(content().string(containsString(products.get(0).getProductName())))
        .andExpect(content().string(containsString(products.get(1).getProductName())));

  }

  @Test
  @DisplayName("Product 생성 페이지를 응답 body에 실어야 한다.")
  void show_product_registration_page() throws Exception {
    mockMvc.perform(get(registerUrl))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("text/html;charset=UTF-8"))
        .andExpect(content().string(containsString(registerUrl)));
  }

  @ParameterizedTest
  @CsvSource({
      "BloodShot Dolce Latte,DRINK,50000,충혈될 정도로 잠이 안 옵니다.",
      "BloodShot Dolce Latte,COFFEE_BEAN_PACKAGE,50000,충혈될 정도로 잠이 안 옵니다.",
      "BloodShot Dolce Latte,SNACK,50000,충혈될 정도로 잠이 안 옵니다.",
  })
  @DisplayName("Product 생성 Post 요청이 성공하면 메인 페이지로 redirect 해야 한다.")
  void post_product_success(String productName, String category, String price, String description)
      throws Exception {

    mockMvc.perform(post(registerUrl)
        .param("productName", productName)
        .param("category", category)
        .param("price", price)
        .param("description", description)
    ).andExpect(status().is3xxRedirection());

    verify(productRepository, times(1)).save(productArgumentCaptor.capture());

    // 확인 1 : DTO 자체를 검증하기 보다는, 컨트롤러가 적합한 Product Entity를 만들었냐에 주목해야 하지 않을까?
    var parsedProduct = productArgumentCaptor.getValue();
    assertThat(parsedProduct.getProductName()).isEqualTo(productName);
    assertThat(parsedProduct.getCategory()).hasToString(category);
    assertThat(parsedProduct.getPrice()).isEqualTo(Long.parseLong(price));
    assertThat(parsedProduct.getDescription()).isEqualTo(description);

  }

  @ParameterizedTest
  @CsvSource({
      "BloodShot Dolce Latte,DRINKed,50000,잘못된 입력 예시 1",
      "Pizza Maru,DRINK, 5000dc, 잘못된 입력 예시 2",
      "Pizza Maru,COFFEEBEANPACKAGE, 50000, enum 입력 잘못된 예시 3"
  })
  @DisplayName("입력 양식이 맞지 않으면 40x 응답을 반환해야 한다.")
  void post_product_failure(String productName, String category, String price, String description)
      throws Exception {

    mockMvc.perform(post(registerUrl)
        .param("productName", productName)
        .param("category", category)
        .param("price", price)
        .param("description", description)
    ).andExpect(status().is4xxClientError());

  }

  @Test
  @DisplayName("저장된 모든 product를 json으로 응답해야 한다.")
  void should_respond_all_products_in_json() throws Exception {
    when(productRepository.findAll()).thenReturn(ProductFixtureList.getAll());

    mockMvc.perform(get("/api/v1/products")).andExpectAll(
        status().is2xxSuccessful(),
        content().contentType("application/json"),
        content().string(
            containsString(ProductFixtureList.get(0).getProductName()))
    );
  }

  @Test
  @DisplayName("Product가 존재하지 않을 경우 비어 있는 리스트를 반환한다.")
  void should_throw_error_respond_all_products_in_json() throws Exception {
    when(productRepository.findAll()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/v1/products")).andExpectAll(
        status().is2xxSuccessful(),
        content().contentType("application/json"),
        content().string("[]")
    );

  }
}
