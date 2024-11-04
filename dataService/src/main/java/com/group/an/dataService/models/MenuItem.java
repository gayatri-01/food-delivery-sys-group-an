package com.group.an.dataService.models;

import org.springframework.data.annotation.Id;

public class MenuItem {
    @Id
    private int menuItemId;
    private String itemName;
    private String description;
    private double price;
    private boolean isAvailable;

    public MenuItem(int menuItemId, String itemName, String description, double price, boolean isAvailable) {
        this.menuItemId = menuItemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
