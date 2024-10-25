package com.group.an.dataService.models;

import org.springframework.data.annotation.Id;

public class OrderItem {
    @Id
    private int orderItemId;
    private int menuItemId;
    private int quantity;
    private double price;

    public OrderItem(int orderItemId, int menuItemId, int quantity, double price) {
        this.orderItemId = orderItemId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
