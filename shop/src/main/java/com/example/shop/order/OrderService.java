package com.example.shop.order;

import com.example.shop.order.dto.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // @Transactional
    public Long createOrder(OrderCreateRequest request) {
        // (옵션) 상품/재고 검증, 회원 검증 등의 로직 가능

        Order order = new Order(
                request.getMemberId(),
                request.getProductId(),
                request.getQuantity()
        );

        orderRepository.save(order);
        return order.getId();
    }

    // @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found: id=" + orderId);
        }
        return order;
    }

    // @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found: id=" + orderId);
        }

        order.cancel();
    }
}
