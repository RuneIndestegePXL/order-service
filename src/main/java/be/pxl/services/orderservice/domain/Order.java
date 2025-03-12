package be.pxl.services.orderservice.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int quantity;
    private long customerId;
    private long productId;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private OrderStatus orderStatus;
    private LocalDateTime updatedAt;

    public Order() {
    }

    public Order(long id, int quantity, long customerId, long productId, LocalDateTime orderDate, String shippingAddress, OrderStatus orderStatus,
                  LocalDateTime updatedAt) {
        this.id = id;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.productId = productId;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", orderDate=" + orderDate +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", orderStatus=" + orderStatus +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
