package com.slicequeue.inflearn.repository.order.query;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
      List<OrderQueryDto> result = findOrders();
      result.forEach(o -> {
        List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
        o.setOrderItems(orderItems);
      });
      return result;
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
      List<OrderQueryDto> result = findOrders();
      List<Long> orderIds = result.stream().map(OrderQueryDto::getOrderId).toList();

      // in 절과 map 화 시켜서 1번만 불리도록 함
      List<OrderItemQueryDto> orderItems = em.createQuery(
              "select new com.slicequeue.inflearn.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
                  +
                  " from OrderItem oi" +
                  " join oi.item i" +
                  " where oi.order.id in :orderIds", OrderItemQueryDto.class)
          .setParameter("orderIds", orderIds).getResultList();
      Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
          .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
      // map 을 활용하여 O(1) 으로 최적화
      result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
      return result;
    }

    public List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new com.slicequeue.inflearn.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

  private List<OrderQueryDto> findOrders() {
    return em.createQuery("select new com.slicequeue.inflearn.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
        " join o.member m" +
        " join o.delivery d", OrderQueryDto.class
    ).getResultList();
  }

  public List<OrderFlatDto> findAllByDto_flat() {
    return em.createQuery(
        "select new com.slicequeue.inflearn.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, oi.item.name, oi.orderPrice, oi.count) "
            + " from Order o"
            + " join o.member m"
            + " join o.delivery d"
            + " join o.orderItems oi"
            + " join oi.item i",
        OrderFlatDto.class
    ).getResultList();
  }
}
