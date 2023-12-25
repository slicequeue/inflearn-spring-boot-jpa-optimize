package com.slicequeue.inflearn.repository.order.query;

import com.slicequeue.inflearn.domain.Address;
import com.slicequeue.inflearn.domain.OrderStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderFlatDto {

  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

  private String itemName;
  private int orderPrice;
  private int count;


  // 정말 한방에 읽어 오기 위해서 flat 하게 DTO 선언 연관관계 제거
  public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus,
      Address address, String itemName, int orderPrice, int count) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.count = count;
  }
}
