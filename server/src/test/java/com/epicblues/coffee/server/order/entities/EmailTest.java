package com.epicblues.coffee.server.order.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EmailTest {

  @ParameterizedTest
  @CsvSource({"wrong format email", "sdfasdfs@naver@naver.com", "hahahoho@naver,com"})
  void email_creation_fail(String incorrectEmailString) {
    assertThatCode(() -> {
      var email = new Email(incorrectEmailString);
    })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("이메일 형식이 아닙니다.");
  }

  @ParameterizedTest
  @CsvSource({"epicblue@hanmail.net", "epicblue.epicblue@naver.naver.com", "cheese@github.co.kr"})
  void email_creation_success(String correctEmailString) {

    var email = new Email(correctEmailString);

    assertThat(email).isInstanceOf(Email.class);
    assertThat(email.getAddress()).isEqualTo(correctEmailString);
  }
}
