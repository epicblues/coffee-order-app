package com.epicblues.coffee.server.order.entities;

import java.util.Objects;
import lombok.Getter;

public class Email {

  private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
  @Getter
  private final String address;

  public Email(String address) {
    if (!address.matches(EMAIL_REGEX)) {
      throw new IllegalArgumentException("이메일 형식이 아닙니다.");
    }
    this.address = address;
  }

  @Override
  public String toString() {
    return this.address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Email)) {
      return false;
    }
    Email email = (Email) o;
    return address.equals(email.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address);
  }

}
