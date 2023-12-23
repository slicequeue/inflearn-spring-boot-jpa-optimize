package com.slicequeue.inflearn.api;

import com.slicequeue.inflearn.domain.Address;
import com.slicequeue.inflearn.domain.Order;
import com.slicequeue.inflearn.domain.OrderStatus;
import com.slicequeue.inflearn.repository.OrderRepository;
import com.slicequeue.inflearn.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * xToOne (ManyToOne, OneToOne) 관계에서 성능 최적화 어떻게 할지 보자!
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();        // Lazy 강제 초기화
            order.getDelivery().getAddress();   // Lazy 강제 초기화
        }

        return all;
        // 양방향 연관관계 문제로 인해서 이대로 하게 되면 무한 루프 형태로 문제 발생
        // -> 해결 방법으로는 1번 양방향 걸리는 곳에 `@JsonIgnore` 처리 해야함
        // -> 2번 1번 설정 이후에 지연로딩에 따른 잭슨 라이브러리 json 직렬화시 프록시 객체가 있어서 문제 발생
        //  -> 이 경우 스프링 부트 3.0 미만인 경우 Hibernate5Module 빈 등록 진행, 관련 gradle 추가 필요
        //  -> 스프링 부트 3.0 이상인 경우 Hibernate5JakartaModule 빈 등록 진행, 관련 gradle 추가 필요
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // ORDER 2개
        // N + 1 -> 1 + 회원 N + 배송 N 
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<SimpleOrderDto> result = orders.stream().map(SimpleOrderDto::new).toList();
        // DTO 전환하여 응답!
        return result;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            // 엔티티가 DTO 생성자의 파라미터로 의존하는 것은 크게 중요하지 않다.
            orderId = order.getId();
            name = order.getMember().getName();             // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();     // LAZY 초기화
        }
    }

}
