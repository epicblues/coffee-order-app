package com.epicblues.coffee.server.order.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Order {

  private final UUID orderId;
  private final LocalDateTime createdAt;
  private final OrderStatus orderStatus;
  private List<OrderItem> orderItems = null;
  private Email email;
  private String address;
  private String postcode;
  private LocalDateTime updatedAt = null;
  public Order(UUID orderId, Email email, String address, String postcode, OrderStatus orderStatus,
      LocalDateTime createdAt, List<OrderItem> orderItems, LocalDateTime updatedAt) {
    this.orderId = orderId;
    this.email = email;
    this.address = address;
    this.postcode = postcode;
    this.orderStatus = orderStatus;
    this.createdAt = createdAt;
    this.orderItems = orderItems;
    this.updatedAt = updatedAt;
  }
  public Order(UUID orderId, Email email, String address, String postcode,
      List<OrderItem> orderItems) {
    this.orderId = orderId;
    this.email = email;
    this.address = address;
    this.postcode = postcode;
    this.createdAt = LocalDateTime.now();
    this.orderItems = orderItems;
    this.orderStatus = OrderStatus.ACCEPTED;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Order)) {
      return false;
    }
    Order order = (Order) o;
    return orderId.equals(order.orderId) && orderStatus == order.orderStatus && email.equals(
        order.email) && address.equals(order.address) && postcode.equals(order.postcode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, orderStatus, email, address, postcode);
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public static class Key {

    public static final String ORDER_ID = "orderId";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String POSTCODE = "postcode";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";

    private Key() {
    }
  }

  public static class Builder {

    private UUID orderId;
    private LocalDateTime createdAt;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private Email email;
    private String address;
    private String postcode;
    private LocalDateTime updatedAt = null;

    private Builder() {
    }

    public static Builder create() {
      return new Builder();
    }

    public Builder orderId(UUID orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder orderItems(List<OrderItem> orderItems) {
      this.orderItems = orderItems;
      return this;
    }

    public Builder orderStatus(OrderStatus orderStatus) {
      this.orderStatus = orderStatus;
      return this;
    }

    public Builder email(Email email) {
      this.email = email;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder postcode(String postcode) {
      this.postcode = postcode;
      return this;
    }

    public Builder updatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Order build() {
      return new Order(orderId, email, address, postcode, orderStatus, createdAt, orderItems,
          updatedAt);
    }
  }
}
