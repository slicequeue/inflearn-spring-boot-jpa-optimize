package com.slicequeue.inflearn.repository;

import com.slicequeue.inflearn.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;          // 회원 이름
    private OrderStatus orderStatus;    // 주문 상태 [ORDER, CANCEL]
}
