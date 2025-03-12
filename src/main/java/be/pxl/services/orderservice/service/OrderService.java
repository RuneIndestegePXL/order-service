package be.pxl.services.orderservice.service;

import be.pxl.services.orderservice.domain.Order;
import be.pxl.services.orderservice.domain.OrderStatus;
import be.pxl.services.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        return orderOpt.orElse(null);
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public List<Order> ordersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

    public Order createOrder(Long productId, Long customerId, int quantity, String shippingAddress) {
        Order order = new Order();
        order.setProductId(productId);
        order.setCustomerId(customerId);
        order.setQuantity(quantity);
        order.setShippingAddress(shippingAddress);
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Integer quantity, String shippingAddress, OrderStatus orderStatus) {
        Optional<Order> orderOpt = orderRepository.findById(Long.valueOf(id));
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (quantity != null) {
                order.setQuantity(quantity);
            }
            if (shippingAddress != null) {
                order.setShippingAddress(shippingAddress);
            }
            if (orderStatus != null) {
                order.setOrderStatus(orderStatus);
            }
            return orderRepository.save(order);
        }
        return null;
    }

    public Boolean cancelOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public List<Order> ordersByCustomer(long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}