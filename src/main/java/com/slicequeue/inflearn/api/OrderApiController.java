package com.slicequeue.inflearn.api;

import com.slicequeue.inflearn.domain.Order;
import com.slicequeue.inflearn.domain.OrderItem;
import com.slicequeue.inflearn.repository.OrderRepository;
import com.slicequeue.inflearn.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            order.getOrderItems().forEach(o -> o.getItem().getName());
        }
        return all;
    }

}
