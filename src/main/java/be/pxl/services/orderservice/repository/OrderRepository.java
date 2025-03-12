package be.pxl.services.orderservice.repository;

import be.pxl.services.orderservice.domain.Order;
import be.pxl.services.orderservice.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByCustomerId(long customerId);
}
