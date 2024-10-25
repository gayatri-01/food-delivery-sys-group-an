package com.group.an.dataService.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Orders {
    @Id
    private int orderId;
    private int customerId;
    private int restaurantId;
    private int personnelId;
    private LocalDateTime orderedAt;
    private LocalDateTime deliveredAt;
    private double price;
    private enum orderStatus { ACCEPTED, PREPARING, READY_FOR_DELIVERY};
    private enum deliveryStatus { PENDING, PICKED_UP, ENROUTE, DELIVERED};
    private List<OrderItem> orderItems;

    public Orders(int orderId, int customerId, int restaurantId, int personnelId, LocalDateTime orderedAt, LocalDateTime deliveredAt, double price, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.personnelId = personnelId;
        this.orderedAt = orderedAt;
        this.deliveredAt = deliveredAt;
        this.price = price;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(int personnelId) {
        this.personnelId = personnelId;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}


