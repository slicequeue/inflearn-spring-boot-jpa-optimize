package com.slicequeue.inflearn.repository.order.query;

import com.slicequeue.inflearn.domain.Address;
import com.slicequeue.inflearn.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "orderId") // 중복 매칭하는 스트림에 사용, 기준으로 orderId를 활용
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
//        this.orderItems = orderItems; // , List<OrderItemQueryDto> orderItems 생략함
    }

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
        OrderStatus orderStatus,
        Address address, List<OrderItemQueryDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }
}
