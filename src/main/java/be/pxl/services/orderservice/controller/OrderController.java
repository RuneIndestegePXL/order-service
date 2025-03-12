package be.pxl.services.orderservice.controller;


import be.pxl.services.orderservice.domain.Order;
import be.pxl.services.orderservice.domain.OrderStatus;
import be.pxl.services.orderservice.service.OrderService;
import com.netflix.graphql.dgs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DgsComponent
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @DgsQuery
    public Order getOrder(@InputArgument long id) {
        log.info("Fetching order with id {}", id);
        return orderService.getOrder(id);
    }

    @DgsQuery
    public List<Order> listOrders() {
        log.info("Fetching all orders");
        return orderService.listOrders();
    }

    @DgsQuery
    public List<Order> ordersByStatus(@InputArgument OrderStatus status) {
        log.info("Fetching orders with status {}", status);
        return orderService.ordersByStatus(status);
    }

    @DgsMutation
    public Order createOrder(
            @InputArgument long productId,
            @InputArgument long customerId,
            @InputArgument Integer quantity,
            @InputArgument String shippingAddress) {
        log.info("Creating order for product {} and customer {}", productId, customerId);
        return orderService.createOrder(productId, customerId, quantity, shippingAddress);
    }

    @DgsMutation
    public Order updateOrder(
            @InputArgument long id,
            @InputArgument Integer quantity,
            @InputArgument String shippingAddress,
            @InputArgument OrderStatus orderStatus) {
        log.info("Updating order with id {}", id);
        return orderService.updateOrder(id, quantity, shippingAddress, orderStatus);
    }

    @DgsMutation
    public Boolean cancelOrder(@InputArgument long id) {
        log.info("Cancelling order with id {}", id);
        return orderService.cancelOrder(id);
    }

    @DgsEntityFetcher(name = "Order")
    public Order resolveOrder(Map<String, Object> reference) {
        log.info("Resolving order with id {}", reference.get("id"));
        Long orderId = Long.parseLong(reference.get("id").toString());
        return orderService.getOrder(orderId);
    }
    @DgsQuery
    public List<Order> ordersByCustomer(@InputArgument String customerId) {
        log.info("Fetching orders for customer {}", customerId);
        return orderService.ordersByCustomer(Long.parseLong(customerId));
    }

    @DgsData(parentType = "Order", field = "customer")
    public Map<String, Object> customer(DgsDataFetchingEnvironment dfe) {
        Order order = dfe.getSource();
        Map<String, Object> customerRef = new HashMap<>();
        customerRef.put("id", String.valueOf(order.getCustomerId()));
        log.info("Fetching customer with id {}", order.getCustomerId());
        return customerRef;
    }

    @DgsData(parentType = "Order", field = "product")
    public Map<String, Object> product(DgsDataFetchingEnvironment dfe) {
        Order order = dfe.getSource();
        Map<String, Object> product = new HashMap<>();
        product.put("id", String.valueOf(order.getProductId()));
        log.info("Fetching product with id {}", order.getProductId());
        return product;
    }

}